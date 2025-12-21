package com.example.library.controllers;

import com.example.library.models.User;
import com.example.library.models.dto.UserDTO;
import com.example.library.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User login and register", description = "Endpoints for login and registering users ")
public class UserController {
    private final UserService userService;
    @Operation(summary = "register a new user")
    @PostMapping("")
    public ResponseEntity<User> signup(UserDTO userDTO){
        User savedUser = userService.signup(userDTO);
        return ResponseEntity
                .created(createLocation(savedUser.getId()))
                .body(savedUser);
    }

    @Operation(summary = "Get all users")
    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    private URI createLocation(Object id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

}
