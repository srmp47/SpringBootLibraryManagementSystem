package com.example.library.models.dto;

import com.example.library.models.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDTO {

    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
            message = "Password must contain uppercase, lowercase and numbers")
    private String password;

    @Schema(implementation = UserRole.class, description = "User role", allowableValues = {"ADMIN", "CUSTOMER"})
    @NotNull(message = "role is required")
    private UserRole role;
}