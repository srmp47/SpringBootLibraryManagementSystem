package com.example.library.controllers;

import com.example.library.models.User;
import com.example.library.models.dto.LoginRequest;
import com.example.library.models.dto.UserDTO;
import com.example.library.repositories.UserRepository;
import com.example.library.security.JwtService;
import com.example.library.services.MonitoringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MonitoringService monitoringService;

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account using DTO and hashes the password"
    )
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDTO dto) {
        monitoringService.incrementTotal();
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            monitoringService.incrementFailure();
            throw new RuntimeException("Username is already taken!");
        }

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole())
                .build();
        userRepository.save(user);
        monitoringService.incrementSuccess();
        return ResponseEntity.ok("User registered successfully");
    }

    @Operation(
            summary = "Login and get JWT token",
            description = "Authenticates user credentials and returns a JWT token if successful"
    )
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        monitoringService.incrementTotal();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        monitoringService.incrementSuccess();
        return ResponseEntity.ok(token);
    }
}