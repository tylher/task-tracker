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

import java.util.Objects;

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
        if(Objects.equals(user.getRole(), "")){
            user.setRole("ROLE_USER");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/signin")
    String login(@RequestBody Login request){
        System.out.println(request.getUsername());
        var res =  new UsernamePasswordAuthenticationToken(request.getUsername()
                ,  request.getPassword());
        System.out.println(res);

        try{
            var authentication = authenticationManager.authenticate(res);
            System.out.println(authentication);
        }catch (Exception e){
            System.out.println(e);
        }
        return "Good";

//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        var principal = (UserPrincipal)authentication.getPrincipal();
//        var token = jwtIssuer.issue(principal.getId(), principal.getUsername());
//        return LoginResponse.builder().token(token).build();
    }
}
