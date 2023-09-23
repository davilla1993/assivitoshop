package com.gbossoufolly.assivitoshopbackend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gbossoufolly.assivitoshopbackend.models.LocalUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiryInSeconds}")
    private int expiryInSeconds;
    private Algorithm algorithm;
    private static final String USERNAME_KEY= "e7ncoOAy/YFvWaTcfIk+LfdAvDPrXKRbE2pQ/p+4E3QJ6nS9z0bULWTOV1yQt+vruffsqVJD8AI2r/dAH/kQSA";
    private static final String VERIFICATION_EMAIL_KEY = "SbYI02b7VXPlt85qrS4pIRi4Pq2vlgNfIyG4ONmHTgIScDK1wfUR3d2TvanjzMqsB4x5HKUBhbARUcQdF/H3eQ";
    private static final String RESET_PASSWORD_EMAIL_KEY = "CPec1pO/kk4HvcOJU32XoBt+TQB+RRHMVDbjjd2cDqfMT2UfiGQWlDxtt1lN8U/dxtzALoCDKefJRI3qo1r+cw";
    @PostConstruct
    public void postConstruct() {

        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    public String generateJWT(LocalUser user) {
        return JWT.create()
                .withClaim(USERNAME_KEY, user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * expiryInSeconds))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public String generateVerificationJWT(LocalUser user) {
        return JWT.create()
                .withClaim(VERIFICATION_EMAIL_KEY, user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expiryInSeconds)))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public String generatePasswordResetJWT(LocalUser user) {
        return JWT.create()
                .withClaim(RESET_PASSWORD_EMAIL_KEY, user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60 * 30)))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public String getResetPasswordEmail(String token) {
        DecodedJWT jwt = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return jwt.getClaim(RESET_PASSWORD_EMAIL_KEY).asString();
    }

    public String getUsername(String token) {
        DecodedJWT jwt = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return jwt.getClaim(USERNAME_KEY).asString();
    }


}
