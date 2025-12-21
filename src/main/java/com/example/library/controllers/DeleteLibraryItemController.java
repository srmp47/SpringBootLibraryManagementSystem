package com.example.library.controllers;

import com.example.library.services.DeleteLibraryItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Tag(name = "Delete an Item", description = "Endpoints for DELETE operation on library items")
public class DeleteLibraryItemController {

    private final DeleteLibraryItemService deleteLibraryItemService;

    @Operation(summary = "Delete a library item by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibraryItem(@PathVariable int id) {
        deleteLibraryItemService.deleteLibraryItem(id);
        return ResponseEntity.noContent().build();
    }
}