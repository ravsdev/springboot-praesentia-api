package com.praesentia.praesentiaapi;

import com.praesentia.praesentiaapi.entity.Role;
import com.praesentia.praesentiaapi.entity.User;
import com.praesentia.praesentiaapi.repository.RecordRepository;
import com.praesentia.praesentiaapi.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void shouldNotAllowToPersistExistingEmail() {
        User user = User.builder()
                .email("admin@praesentia.org")
                .password(passwordEncoder.encode("admin"))
                .firstname("Admin")
                .lastname("admin")
                .dni("12345678A")
                .role(Role.ADMIN)
                .build();

        Assertions.assertThrows(DataIntegrityViolationException.class,
                ()->userRepository.save(user));
    }
}
