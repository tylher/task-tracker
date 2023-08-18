package dev.damola.tasktracker.controller;

import dev.damola.tasktracker.configuration.JwtIssuer;
import dev.damola.tasktracker.configuration.UserPrincipal;
import dev.damola.tasktracker.model.Login;
import dev.damola.tasktracker.model.LoginResponse;
import dev.damola.tasktracker.model.UserEntity;
import dev.damola.tasktracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private JwtIssuer jwtIssuer;
    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody  UserEntity user){
        if(userRepository.existsByUsername(user.getUsername())){
            return ResponseEntity.badRequest().body("Username has been taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/signin")
    LoginResponse login(@RequestBody Login request){
        System.out.println(request.getUsername());
        var authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(request.getUsername()
                        ,  request.getPassword())
        );
        System.out.println(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrincipal)authentication.getPrincipal();
        System.out.println(principal.getUsername());
        var token = jwtIssuer.issue(principal.getId(), principal.getUsername());
        System.out.println(token);
        return LoginResponse.builder().token(token).build();
    }
}
