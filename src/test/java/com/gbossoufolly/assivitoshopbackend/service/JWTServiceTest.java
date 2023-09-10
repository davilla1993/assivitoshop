package com.gbossoufolly.assivitoshopbackend.service;

import com.gbossoufolly.assivitoshopbackend.models.LocalUser;
import com.gbossoufolly.assivitoshopbackend.repository.LocalUserRepository;
import com.gbossoufolly.assivitoshopbackend.services.JWTService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JWTServiceTest {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private LocalUserRepository localUserRepository;
    @Test
    public void testVerificationTokenNotUsableForLogin() {
        LocalUser user = localUserRepository.findByUsernameIgnoreCase("UserA").get();
        String token = jwtService.generateVerificationJWT(user);
        Assertions.assertNull(jwtService.getUsername(token), "Verification token should not contain username.");
    }

    /**
     * Tests that the authentication token generate still returns the username.
     */
    @Test
    public void testAuthTokenReturnsUsername() {
        LocalUser user = localUserRepository.findByUsernameIgnoreCase("UserA").get();
        String token = jwtService.generateJWT(user);
        Assertions.assertEquals(user.getUsername(), jwtService.getUsername(token), "Token for auth should contain users username.");
    }
}
