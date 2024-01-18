package com.praesentia.praesentiaapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.praesentia.praesentiaapi.auth.request.LoginRequest;
import com.praesentia.praesentiaapi.auth.request.RegisterRequest;
import com.praesentia.praesentiaapi.auth.response.AuthResponse;
import com.praesentia.praesentiaapi.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;
/*
    @GetMapping("")
    public String test() {
        return "Auth controller works!";
    }
*/
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
/*
    @PostMapping(value = "register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }*/
}
