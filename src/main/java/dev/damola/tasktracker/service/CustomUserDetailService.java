package dev.damola.tasktracker.service;

import dev.damola.tasktracker.configuration.UserPrincipal;
import dev.damola.tasktracker.model.UserEntity;
import dev.damola.tasktracker.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private UserRepository userRepository;

    public  CustomUserDetailService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         var userEntity = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found with"+ username));

        return UserPrincipal.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(userEntity.getRole())))
                .build();
    }
}
