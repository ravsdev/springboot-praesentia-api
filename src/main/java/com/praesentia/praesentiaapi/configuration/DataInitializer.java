package com.praesentia.praesentiaapi.configuration;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.praesentia.praesentiaapi.entity.Role;
import com.praesentia.praesentiaapi.entity.User;
import com.praesentia.praesentiaapi.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User admin = User.builder()
                .email("admin@praesentia.org")
                .password(passwordEncoder.encode("admin"))
                .firstname("Admin")
                .lastname("admin")
                .dni("12345678A")
                .role(Role.ADMIN)
                .build();

        Optional<User> user = userRepository.findByEmail("admin@praesentia.org");

        if (admin != null && user.isEmpty())
            userRepository.save(admin);

        System.out.println(" ApplicationRunner called");
    }
}