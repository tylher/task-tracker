package dev.damola.tasktracker.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;


@Component
public class JwtIssuer {
    @Value("${jwt.secret}")
    private String jwtSecret;
    public String issue(Long id, String username){
       return JWT.create().withSubject(String.valueOf(id))
                .withExpiresAt(Instant.now().plus(Duration.of(120, ChronoUnit.MINUTES)))
                .withClaim("username",username)
                .sign(Algorithm.HMAC256(jwtSecret));
    }
}
