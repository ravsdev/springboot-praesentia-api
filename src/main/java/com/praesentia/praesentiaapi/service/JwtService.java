package com.praesentia.praesentiaapi.service;

import com.praesentia.praesentiaapi.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String getEmailFromToken(String token);

    String getToken(User user);

    boolean isTokenValid(String token, UserDetails userDetails);
}
