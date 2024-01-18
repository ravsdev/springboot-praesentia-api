package com.praesentia.praesentiaapi.service.Impl;

import java.util.List;
import java.util.Optional;

import com.praesentia.praesentiaapi.exceptions.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.praesentia.praesentiaapi.entity.User;
import com.praesentia.praesentiaapi.repository.UserRepository;
import com.praesentia.praesentiaapi.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable page) {
        return userRepository.findAll(page);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {

        return userRepository.findById(id);
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {

        return userRepository.findByEmail(email);
    }
    @Override
    public User save(User user) {
        User newUser = User.builder()
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .dni(user.getDni())
                .role(user.getRole())
                .build();
        return userRepository.save(newUser);
    }

    @Override
    public User update(User user) {
        User updateUser = userRepository.findById(user.getId())
                .orElseThrow(
                        () -> new NotFoundException("Usuario con ID: " + user.getId() + " no encontrado."));
        updateUser.setDni(user.getDni());
        updateUser.setFirstname(user.getFirstname());
        updateUser.setLastname(user.getLastname());
        updateUser.setEmail(user.getEmail());

        return userRepository.save(updateUser);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Usuario con ID: " + id + " no encontrado."));
        userRepository.deleteById(id);
    }

}
