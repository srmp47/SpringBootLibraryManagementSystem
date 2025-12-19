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
public class ReferenceDTO extends LibraryItemDTO {
    @NotBlank(message = "Reference type is required")
    private String referenceType;

    private String edition;
    private String subject;
}