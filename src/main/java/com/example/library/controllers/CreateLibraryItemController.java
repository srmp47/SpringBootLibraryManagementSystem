package com.example.library.controllers;

import com.example.library.models.Book;
import com.example.library.models.Magazine;
import com.example.library.models.Reference;
import com.example.library.models.Thesis;
import com.example.library.models.dto.BookDTO;
import com.example.library.models.dto.MagazineDTO;
import com.example.library.models.dto.ReferenceDTO;
import com.example.library.models.dto.ThesisDTO;
import com.example.library.services.CreateLibraryItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/create")
@RequiredArgsConstructor
@Tag(name = "Create New Items", description = "Endpoints for CREATE operation on library items")
public class CreateLibraryItemController {
    private final CreateLibraryItemService createLibraryItemService;

    @Operation(summary = "Add a new bookDTO")
    @PostMapping("book")
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@Valid @RequestBody BookDTO bookDTO) {
        return createLibraryItemService.createBook(bookDTO);
    }

    @Operation(summary = "Add a new thesisDTO")
    @PostMapping("thesis")
    @ResponseStatus(HttpStatus.CREATED)
    public Thesis createThesis(@Valid @RequestBody ThesisDTO thesisDTO) {
        return createLibraryItemService.createThesis(thesisDTO);
    }

    @Operation(summary = "Add a new magazineDTO")
    @PostMapping("magazine")
    @ResponseStatus(HttpStatus.CREATED)
    public Magazine createMagazine(@Valid @RequestBody MagazineDTO magazineDTO) {
        return createLibraryItemService.createMagazine(magazineDTO);
    }

    @Operation(summary = "Add a new referenceDTO")
    @PostMapping("reference")
    @ResponseStatus(HttpStatus.CREATED)
    public Reference createReference(@Valid @RequestBody ReferenceDTO referenceDTO) {
        return createLibraryItemService.createReference(referenceDTO);
    }
    

}
