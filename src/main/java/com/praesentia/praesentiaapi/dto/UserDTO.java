package com.praesentia.praesentiaapi.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.praesentia.praesentiaapi.entity.Record;
import com.praesentia.praesentiaapi.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    @NotNull
    @NotEmpty
    private String firstname;

    @NotNull
    @NotEmpty
    private String lastname;

    @Email
    @NotNull(message = "E-mail required")
    @NotEmpty(message = "E-mail no puede estar vacío")
    private String email;

    @NotNull
    @NotEmpty
    private String dni;

    @NotNull(message = "Role required")
    private Role role = Role.EMPLOYEE;

    @NotNull
    private boolean enabled;

}
