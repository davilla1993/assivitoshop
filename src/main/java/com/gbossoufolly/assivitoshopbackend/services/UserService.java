package com.gbossoufolly.assivitoshopbackend.services;

import com.gbossoufolly.assivitoshopbackend.api.models.LoginBody;
import com.gbossoufolly.assivitoshopbackend.api.models.PasswordResetBody;
import com.gbossoufolly.assivitoshopbackend.api.models.RegistrationBody;
import com.gbossoufolly.assivitoshopbackend.exceptions.EmailFailureException;
import com.gbossoufolly.assivitoshopbackend.exceptions.EmailNotFoundException;
import com.gbossoufolly.assivitoshopbackend.exceptions.UserAlreadyExistsException;
import com.gbossoufolly.assivitoshopbackend.exceptions.UserNotVerifiedException;
import com.gbossoufolly.assivitoshopbackend.models.LocalUser;
import com.gbossoufolly.assivitoshopbackend.models.VerificationToken;
import com.gbossoufolly.assivitoshopbackend.repository.LocalUserRepository;
import com.gbossoufolly.assivitoshopbackend.repository.VerificationTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final LocalUserRepository localUserRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EncryptionService encryptionService;
    private final JWTService jwtService;
    private final EmailService emailService;

    public UserService(LocalUserRepository userRepository, VerificationTokenRepository verificationTokenRepository,
                       EncryptionService encryptionService, JWTService jwtService, EmailService emailService) {
        this.localUserRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException, EmailFailureException {

        if(localUserRepository.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent() ||
            localUserRepository.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        LocalUser user = new LocalUser();

        user.setEmail(registrationBody.getEmail());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setUsername(registrationBody.getUsername());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));

        // Send verification e-mail to the user
        VerificationToken verificationToken = createVerificationToken(user);
        emailService.sendVerificationEmail(verificationToken);
        return localUserRepository.save(user);
    }

    public String loginUser(LoginBody loginBody) throws UserNotVerifiedException, EmailFailureException {
        Optional<LocalUser> opUser = localUserRepository.findByUsernameIgnoreCase(loginBody.getUsername());
        if(opUser.isPresent()) {
            LocalUser user = opUser.get();
            if(encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                if(user.isEmailVerified()) {
                return jwtService.generateJWT(user);
                } else {
                    List<VerificationToken> verificationTokens = user.getVerificationTokens();
                    boolean resend = verificationTokens.size() == 0 ||
                            verificationTokens.get(0).getCreatedTimestamp().before(
                                    new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000)));
                    if(resend) {
                        VerificationToken verificationToken = createVerificationToken(user);
                        verificationTokenRepository.save(verificationToken);
                        emailService.sendVerificationEmail(verificationToken);
                    }
                    throw new UserNotVerifiedException(resend);
                }
            }
        }
        return null;
    }

    private VerificationToken createVerificationToken(LocalUser user) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJWT(user));
        verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setUser(user);
        user.getVerificationTokens().add(verificationToken);

        return verificationToken;
    }

    @Transactional
    public boolean verifyUser(String token) {
        Optional<VerificationToken> opToken = verificationTokenRepository.findByToken(token);
        if(opToken.isPresent()) {
            VerificationToken verificationToken = opToken.get();
            LocalUser user = verificationToken.getUser();
            if(!user.isEmailVerified()) {
                user.setEmailVerified(true);
                localUserRepository.save(user);
                verificationTokenRepository.deleteByUser(user);
                return true;
            }
        }
        return false;
    }

    public void forgotPassword(String email) throws EmailNotFoundException {
        Optional<LocalUser> opUser = localUserRepository.findByEmailIgnoreCase(email);
        if(opUser.isPresent()) {
            LocalUser user = opUser.get();
            String token = jwtService.generatePasswordResetJWT(user);
            emailService.sendPasswordResetEmail(user, token);
        } else {
            throw new EmailNotFoundException();
        }
    }

    public void resetPassword(PasswordResetBody body) {
        String email = jwtService.getResetPasswordEmail(body.getToken());
        Optional<LocalUser> opUser = localUserRepository.findByEmailIgnoreCase(email);
        if(opUser.isPresent()) {
            LocalUser user = opUser.get();
            user.setPassword(encryptionService.encryptPassword(body.getPassword()));
            localUserRepository.save(user);
        }
    }

    public boolean userHasPermissionToUser(LocalUser user, Long id) {
        return user.getId() == id;
    }
}
