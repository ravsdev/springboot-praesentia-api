package com.praesentia.praesentiaapi.service;

import java.util.List;
import java.util.Optional;

import com.praesentia.praesentiaapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    List<User> findAll();

    Page<User> findAll(Pageable page);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
    Optional<User> findByDni(String dni);

    User save(User user);

    User update(User user);

    void deleteById(Long id);
}
