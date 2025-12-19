package com.example.library.models.dto;

import com.example.library.models.enums.LibraryItemStatus;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class LibraryItemDTO {
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    private LocalDate publishDate;

}