package com.example.library.controllers;

import com.example.library.models.User;
import com.example.library.models.dto.UserDTO;
import com.example.library.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "Endpoints for user registration and profile management")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Register a new user", description = "Creates a new user account in the system")
    @PostMapping("/register")
    public ResponseEntity<User> signup(@RequestBody UserDTO userDTO) {
        User savedUser = userService.signup(userDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).body(savedUser);
    }

    @Operation(summary = "List all users", description = "Retrieves a complete list of registered users in the library")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}