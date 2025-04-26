package ru.kpfu.itis.kononenko.shedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.kononenko.shedule.dto.SignUpDto;
import ru.kpfu.itis.kononenko.shedule.model.User;
import ru.kpfu.itis.kononenko.shedule.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(SignUpDto signUpDto) {
        userRepository.findByUsername(signUpDto.getUsername()).ifPresent(user -> {
           throw new RuntimeException("Username already exists");
        });
        userRepository.findByEmail(signUpDto.getEmail()).ifPresent(user -> {
            throw new RuntimeException("Email already exists");
        });
        return userRepository.save(User.builder()
                .email(signUpDto.getEmail())
                .username(signUpDto.getUsername())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .emailVerified(false)
                .build());
    }

    public List<User> findAllVerified(){
        return userRepository.findAllByEmailVerifiedTrue();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username)
                .map(user -> org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRoles().stream().map(String::valueOf).toArray(String[]::new))
                        .build()
                )
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
    }



}
