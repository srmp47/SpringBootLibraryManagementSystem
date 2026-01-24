package com.example.library.controllers;

import com.example.library.models.LibraryItemView;
import com.example.library.models.User;
import com.example.library.repositories.LibraryItemViewRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Library Reports", description = "Read-only views and statistics using database views")
public class LibraryItemViewController {

    private final LibraryItemViewRepository libraryItemViewRepository;

    @Operation(summary = "Get full library summary",
            description = "Returns a flat view of all items joined with current borrower information")
    @GetMapping("/all-info")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<LibraryItemView>> getAllWithUserInfo(
            @AuthenticationPrincipal User currentUser) {

        var results = libraryItemViewRepository.findAll();
        return ResponseEntity.ok(results);
    }

    @Operation(summary = "List currently borrowed items",
            description = "Shows all items that are currently out with users")
    @GetMapping("/borrowed")
    public ResponseEntity<List<List<LibraryItemView>>> getBorrowed() {

        var results = libraryItemViewRepository.findBorrowedItems();
        return ResponseEntity.ok(List.of(results));
    }

    @Operation(summary = "List available items",
            description = "Returns a list of all items currently on the shelves (Status = EXIST)")
    @GetMapping("/available")
    public ResponseEntity<List<LibraryItemView>> getAvailable() {

        var results = libraryItemViewRepository.findAvailableItems();
        return ResponseEntity.ok(results);
    }

    @Operation(summary = "Get items by specific username",
            description = "Retrieves all items currently borrowed by a specific user's username")
    @GetMapping("/by-user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<LibraryItemView>> getByUser(
            @RequestParam String username,
            @AuthenticationPrincipal User currentUser) {

        var results = libraryItemViewRepository.findItemsBorrowedByUser(username);
        return ResponseEntity.ok(results);
    }
}
