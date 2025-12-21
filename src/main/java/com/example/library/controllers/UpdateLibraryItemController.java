package com.example.library.controllers;


import com.example.library.exceptions.BusinessRuleException;
import com.example.library.models.LibraryItem;
import com.example.library.models.enums.LibraryItemStatus;
import com.example.library.services.UpdateLibraryItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patch")
@RequiredArgsConstructor
@Tag(name = "Library item Partial Updates", description = "Endpoints for PATCH operation on library items")
public class UpdateLibraryItemController {
    private final UpdateLibraryItemService updateLibraryItemService;

    @Operation(summary = "Change library item status")
    @PatchMapping("/{id}/status/{newStatus}")
    public ResponseEntity<LibraryItem> patchLibraryItemStatus(
            @PathVariable int id,
            @PathVariable LibraryItemStatus newStatus) {
        LibraryItem libraryItem = updateLibraryItemService.patchLibraryItemStatus(id, newStatus);
        return ResponseEntity.ok(libraryItem);
    }
}