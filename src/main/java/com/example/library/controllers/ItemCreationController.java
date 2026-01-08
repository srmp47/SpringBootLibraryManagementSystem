package com.example.library.controllers;

import com.example.library.models.*;
import com.example.library.models.dto.*;
import com.example.library.services.CreateLibraryItemService;
import com.example.library.services.MonitoringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@Tag(name = "Item Creation", description = "Endpoints for creating specific types of library items")
public class ItemCreationController {

    private final CreateLibraryItemService createLibraryItemService;
    private final MonitoringService monitoringService;

    @Operation(summary = "Add a new book", description = "Creates a new book record with ISBN and page count details")
    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookDTO dto, @AuthenticationPrincipal User currentUser) {
        monitoringService.incrementTotal();
        Book saved = createLibraryItemService.createBook(dto);
        monitoringService.incrementSuccess(currentUser.getUsername());
        return ResponseEntity.created(createLocation(saved.getId())).body(saved);
    }

    @Operation(summary = "Add a new thesis", description = "Creates a new thesis record including university and advisor information")
    @PostMapping("/theses")
    public ResponseEntity<Thesis> createThesis(@Valid @RequestBody ThesisDTO dto, @AuthenticationPrincipal User currentUser) {
        monitoringService.incrementTotal();
        Thesis saved = createLibraryItemService.createThesis(dto);
        monitoringService.incrementSuccess(currentUser.getUsername());
        return ResponseEntity.created(createLocation(saved.getId())).body(saved);
    }

    @Operation(summary = "Add a new magazine", description = "Creates a new magazine entry with issue number and publisher details")
    @PostMapping("/magazines")
    public ResponseEntity<Magazine> createMagazine(@Valid @RequestBody MagazineDTO dto, @AuthenticationPrincipal User currentUser) {
        monitoringService.incrementTotal();
        Magazine saved = createLibraryItemService.createMagazine(dto);
        monitoringService.incrementSuccess(currentUser.getUsername());
        return ResponseEntity.created(createLocation(saved.getId())).body(saved);
    }

    @Operation(summary = "Add a new reference", description = "Creates a new reference item like encyclopedias or dictionaries")
    @PostMapping("/references")
    public ResponseEntity<Reference> createReference(@Valid @RequestBody ReferenceDTO dto, @AuthenticationPrincipal User currentUser) {
        monitoringService.incrementTotal();
        Reference saved = createLibraryItemService.createReference(dto);
        monitoringService.incrementSuccess(currentUser.getUsername());
        return ResponseEntity.created(createLocation(saved.getId())).body(saved);
    }

    private URI createLocation(Object id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }
}