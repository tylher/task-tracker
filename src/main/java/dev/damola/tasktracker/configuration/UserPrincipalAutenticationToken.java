package dev.damola.tasktracker.configuration;

import org.springframework.security.authentication.AbstractAuthenticationToken;


public class UserPrincipalAutenticationToken extends AbstractAuthenticationToken {
    private final UserPrincipal userPrincipal;

    public UserPrincipalAutenticationToken(UserPrincipal userPrincipal) {
        super(userPrincipal.getAuthorities());
        this.userPrincipal = userPrincipal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public UserPrincipal getPrincipal() {
        return userPrincipal;
    }
}
