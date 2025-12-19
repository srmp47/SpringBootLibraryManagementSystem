package com.example.library.models.dto;

import com.example.library.models.dto.LibraryItemDTO;
import jakarta.validation.constraints.Min;
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
public class BookDTO extends LibraryItemDTO {
    @NotBlank(message = "ISBN is required")
    private String isbn;

    private String genre;

    @Min(value = 1, message = "Page count must be at least 1")
    private int pageCount;
}