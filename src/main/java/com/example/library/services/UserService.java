package com.example.library.services;

import com.example.library.exceptions.DuplicateResourceException;
import com.example.library.models.User;
import com.example.library.models.dto.UserDTO;
import com.example.library.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.image.RescaleOp;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    @Transactional
    public User signup(UserDTO userDTO){
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new DuplicateResourceException("User with username " + userDTO.getUsername() + " already exists");
        }
        User user = User.builder().password(userDTO.getPassword())
                .username(userDTO.getUsername())
                .build();
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

}
