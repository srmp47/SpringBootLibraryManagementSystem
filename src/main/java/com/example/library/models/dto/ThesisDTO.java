package com.example.library.models.dto;

import com.example.library.models.dto.LibraryItemDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ThesisDTO extends LibraryItemDTO {
    @NotBlank(message = "University name is required")
    private String university;

    @NotBlank(message = "Department is required")
    private String department;

    private String advisor;
}