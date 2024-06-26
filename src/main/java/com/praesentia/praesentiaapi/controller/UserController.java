package com.praesentia.praesentiaapi.controller;

import com.praesentia.praesentiaapi.auth.response.ApiResponse;
import com.praesentia.praesentiaapi.dto.UserDTO;
import com.praesentia.praesentiaapi.dto.UserRecordDTO;
import com.praesentia.praesentiaapi.entity.Role;
import com.praesentia.praesentiaapi.entity.User;
import com.praesentia.praesentiaapi.exceptions.AlreadyExistsException;
import com.praesentia.praesentiaapi.exceptions.NotFoundException;
import com.praesentia.praesentiaapi.service.UserService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/users")
@Validated
@CrossOrigin(origins = {"http://localhost:4200"})
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR')")
    public ResponseEntity<List<?>> getAll(@RequestParam(name="records", defaultValue = "false", required = false) Boolean records) {
        List<User> users = userService.findAll();
        if (users.isEmpty())
            return ResponseEntity.noContent().build();

        if(records) {
            List<UserRecordDTO> usersRecordsDTO = users.stream()
                    .map(user -> modelMapper.map(user, UserRecordDTO.class))
                    .toList();
            return ResponseEntity.ok(usersRecordsDTO);
        }

        List<UserDTO> usersDTO = users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
        return ResponseEntity.ok(usersDTO);
    }

    @GetMapping("{id}")
    @PreAuthorize("(#id == authentication.principal.id) or hasRole('ADMIN')")
    public ResponseEntity<?> getById(@PathVariable(name = "id") Long id, @RequestParam(name="records", defaultValue = "false", required = false) Boolean records) {
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // User authUser = (User) authentication.getPrincipal();

        Optional<User> user = userService.findById(id);
        if (user.isEmpty())
            return ResponseEntity.noContent().build();

        if(records) {
            return ResponseEntity.ok(modelMapper.map(user, UserRecordDTO.class));
        }
        return ResponseEntity.ok(modelMapper.map(user, UserDTO.class));
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody User user) {
        userService.findByEmail(user.getEmail()).ifPresent(p -> {
            throw new AlreadyExistsException("El correo electrónico: " + p.getEmail() + " ya existe");
        });

        userService.findByDni(user.getDni()).ifPresent(p -> {
            throw new AlreadyExistsException("El DNI: " + p.getDni() + " ya está asignado a otro usuario.");
        });

        User newUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(newUser, UserDTO.class));
    }

    @PutMapping("{id}")
    @PreAuthorize("(#id == authentication.principal.id) or hasRole('ADMIN')")
    public ResponseEntity<UserDTO> update(
            @PathVariable(name = "id") Long id,
            @RequestBody UserDTO userDTO,
            Authentication authentication) {
        Optional<User> userDB = userService.findById(id);
        User authUser = (User) authentication.getPrincipal();

        if (userDB.isEmpty())
            return ResponseEntity.notFound().build();
        
        if(userDTO.getId() == null)
            userDTO.setId(id);

        if(authUser.getRole() != Role.ADMIN){
            userDTO.setRole(userDB.get().getRole());
            userDTO.setEnabled(userDB.get().getEnabled());
        }

        User updateUser = userService.update(modelMapper.map(userDTO, User.class));

        return ResponseEntity.ok(modelMapper.map(updateUser, UserDTO.class));

    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(
            @PathVariable Long id) {
        Optional<User> user = userService.findById(id);

        if (user.isEmpty())
            return ResponseEntity.notFound().build();

       // Boolean noRecords = user.get().getRecords().isEmpty();

        if(!user.get().getRecords().isEmpty())
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario tiene registros.");

        userService.deleteById(id);
        return ResponseEntity.ok(ApiResponse
                .builder()
                .status(200)
                .message("User deleted")
                .build());

    }
}
