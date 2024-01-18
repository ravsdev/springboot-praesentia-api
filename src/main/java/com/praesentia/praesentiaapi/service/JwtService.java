package com.praesentia.praesentiaapi.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String getEmailFromToken(String token);

    String getToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);
}
