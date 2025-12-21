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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Create New Items", description = "Endpoints for CREATE operation on library items")
public class CreateLibraryItemController {

    private final CreateLibraryItemService createLibraryItemService;

    @Operation(summary = "Add a new book")
    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookDTO bookDTO) {
        Book savedBook = createLibraryItemService.createBook(bookDTO);
        return ResponseEntity
                .created(createLocation(savedBook.getId()))
                .body(savedBook);
    }

    @Operation(summary = "Add a new thesis")
    @PostMapping("/theses")
    public ResponseEntity<Thesis> createThesis(@Valid @RequestBody ThesisDTO thesisDTO) {
        Thesis savedThesis = createLibraryItemService.createThesis(thesisDTO);
        return ResponseEntity
                .created(createLocation(savedThesis.getId()))
                .body(savedThesis);
    }

    @Operation(summary = "Add a new magazine")
    @PostMapping("/magazines")
    public ResponseEntity<Magazine> createMagazine(@Valid @RequestBody MagazineDTO magazineDTO) {
        Magazine savedMagazine = createLibraryItemService.createMagazine(magazineDTO);
        return ResponseEntity
                .created(createLocation(savedMagazine.getId()))
                .body(savedMagazine);
    }

    @Operation(summary = "Add a new reference")
    @PostMapping("/references")
    public ResponseEntity<Reference> createReference(@Valid @RequestBody ReferenceDTO referenceDTO) {
        Reference savedReference = createLibraryItemService.createReference(referenceDTO);
        return ResponseEntity
                .created(createLocation(savedReference.getId()))
                .body(savedReference);
    }
    private URI createLocation(Object id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}