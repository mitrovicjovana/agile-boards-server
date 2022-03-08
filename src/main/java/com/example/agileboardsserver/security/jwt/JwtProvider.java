package com.example.agileboardsserver.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.agileboardsserver.security.service.UserPrincipal;

import java.time.Instant;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtProvider {

//    @Value("${jwt.secret}")
    private final String secret = "abc";

//    @Value("${jwt.expiration.time}")
    private final long jwtExpirationTime = 8640000;

    public String generateJwtToken(UserPrincipal userPrincipal){
        Algorithm algorithm = HMAC512(secret);
        return JWT.create()
                .withIssuedAt(Date.from(Instant.now()))
                .withSubject(userPrincipal.getUsername())
                .withExpiresAt(Date.from(Instant.now().plusMillis(jwtExpirationTime)))
                .sign(algorithm);
    }
}
