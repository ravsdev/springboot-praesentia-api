package com.praesentia.praesentiaapi.auth.request;

import com.praesentia.praesentiaapi.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String email;
    String password;
    String firstname;
    String lastname;
    String dni;
    Role role;
}