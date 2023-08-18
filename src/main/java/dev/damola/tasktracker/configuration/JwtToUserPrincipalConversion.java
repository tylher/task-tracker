package dev.damola.tasktracker.configuration;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

@Component
public class JwtToUserPrincipalConversion {
    public UserPrincipal convert(DecodedJWT jwt){
        UserPrincipal user =  UserPrincipal.builder()
                .id(Long.valueOf(jwt.getSubject()))
                .username(jwt.getClaim("username").asString())
                .build();

        System.out.println(user.getUsername());
        return user;
    }
}
