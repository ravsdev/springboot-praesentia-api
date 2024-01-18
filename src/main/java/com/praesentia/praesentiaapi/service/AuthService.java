package com.praesentia.praesentiaapi.service;

import com.praesentia.praesentiaapi.auth.request.LoginRequest;
import com.praesentia.praesentiaapi.auth.request.RegisterRequest;
import com.praesentia.praesentiaapi.auth.response.AuthResponse;

public interface AuthService {
    public AuthResponse login(LoginRequest request);

    public AuthResponse register(RegisterRequest request);
}
