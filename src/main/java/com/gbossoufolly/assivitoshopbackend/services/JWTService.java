package com.gbossoufolly.assivitoshopbackend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
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
    private static final String USERNAME_KEY= "e7ncoOAy/YFvWaTcfIk+LfdAvDPrXKRbE2pQ/p+4E3QJ6nS9z0bULWTOV1yQt+vruffsqVJD8AI2r/dAH/kQSA==";
    private static final String EMAIL_KEY = "SbYI02b7VXPlt85qrS4pIRi4Pq2vlgNfIyG4ONmHTgIScDK1wfUR3d2TvanjzMqsB4x5HKUBhbARUcQdF/H3eQ==";

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

    public String getUsername(String token) {
        return JWT.decode(token).getClaim(USERNAME_KEY).asString();
    }

    public String generateVerificationJWT(LocalUser user) {
        return JWT.create()
                .withClaim(EMAIL_KEY, user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * expiryInSeconds))
                .withIssuer(issuer)
                .sign(algorithm);
    }

}
