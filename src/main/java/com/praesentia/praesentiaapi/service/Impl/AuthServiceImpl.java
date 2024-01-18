package com.praesentia.praesentiaapi.service.Impl;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.praesentia.praesentiaapi.auth.request.LoginRequest;
import com.praesentia.praesentiaapi.auth.request.RegisterRequest;
import com.praesentia.praesentiaapi.auth.response.AuthResponse;
import com.praesentia.praesentiaapi.entity.User;
import com.praesentia.praesentiaapi.repository.UserRepository;
import com.praesentia.praesentiaapi.service.AuthService;
import com.praesentia.praesentiaapi.service.JwtService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
        private final UserRepository userRepository;
        private final JwtService jwtService;
        private final PasswordEncoder passwordEncoder;
        private final AuthenticationManager authenticationManager;

        public AuthResponse login(LoginRequest request) {
                authenticationManager
                                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                                                request.getPassword()));
                UserDetails user = userRepository.findByEmail(request.getEmail())
                                .orElseThrow();
                String token = jwtService.getToken(user);

                return AuthResponse.builder()
                                .token(token)
                                .build();

        }

        public AuthResponse register(RegisterRequest request) {
                User user = User.builder()
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .firstname(request.getFirstname())
                                .lastname(request.getLastname())
                                .dni(request.getDni())
                                .role(request.getRole())
                                .build();

                userRepository.save(user);

                return AuthResponse.builder()
                                .token(jwtService.getToken(user))
                                .build();

        }

}
