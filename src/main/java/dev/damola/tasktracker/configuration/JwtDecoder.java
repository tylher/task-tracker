package dev.damola.tasktracker.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtDecoder {
    @Value("${jwt.secret}")
    private String jwtSecret;
    public DecodedJWT decode(String token){
        return JWT.require(Algorithm.HMAC256(jwtSecret)).build().verify(token);
    }
}
