package com.gbossoufolly.assivitoshopbackend.api.controllers.auth;

import com.gbossoufolly.assivitoshopbackend.api.models.LoginBody;
import com.gbossoufolly.assivitoshopbackend.api.models.LoginResponse;
import com.gbossoufolly.assivitoshopbackend.api.models.RegistrationBody;
import com.gbossoufolly.assivitoshopbackend.exceptions.EmailFailureException;
import com.gbossoufolly.assivitoshopbackend.exceptions.UserAlreadyExistsException;
import com.gbossoufolly.assivitoshopbackend.exceptions.UserNotVerifiedException;
import com.gbossoufolly.assivitoshopbackend.models.LocalUser;
import com.gbossoufolly.assivitoshopbackend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody) {

        try {
            userService.registerUser(registrationBody);
            return ResponseEntity.ok().build();
        } catch(UserAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch ( EmailFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody) {

        String jwt = null;
        LoginResponse response;

        try {
            jwt = userService.loginUser(loginBody);
        } catch (UserNotVerifiedException ex) {
             response = new LoginResponse();
            response.setSuccess(false);
            String reason = "USER_NOT¨VERIFIED";
            if(ex.isNewEmailSent()) {
                reason += "_EMAIL_RESENT";
            }
            response.setFailureReason(reason);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);

        } catch (EmailFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if(jwt == null) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {

            response = new LoginResponse();
            response.setJwt(jwt);
            response.setSuccess(true);
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/verify")
    public ResponseEntity verifyEmail(@RequestParam String token) {
        if(userService.verifyUser(token)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @GetMapping("/me")
    public LocalUser getLoggedInUserProfile(@AuthenticationPrincipal LocalUser user) {

        return user;
    }
}
