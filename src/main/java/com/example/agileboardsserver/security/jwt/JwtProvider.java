package com.example.agileboardsserver.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.agileboardsserver.security.service.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Component
public class JwtProvider {

    //    @Value("${jwt.secret}")
    private final String secret = "abc";
    private final Algorithm algorithm = HMAC512(secret);
    //    @Value("${jwt.expiration.time}")
    private final long jwtExpirationTime = 864000000;

    public String generateJwtToken(Authentication authentication) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return JWT.create()
                .withIssuedAt(new Date())
                .withSubject(principal.getUsername())
                .withExpiresAt(Date.from(Instant.now().plusMillis(jwtExpirationTime)))
                .sign(algorithm);
    }

    public boolean isTokenValid(String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            return !isTokenExpired(jwtVerifier, token);
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token can not be verified");
        }
    }

    public String getUsernameFromToken(String token) {
        return JWT.decode(token).getSubject();
    }

    private boolean isTokenExpired(JWTVerifier jwtVerifier, String token) {
        Date expiration = jwtVerifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }
}
