package com.example.library.controllers;

import com.example.library.models.LibraryItem;
import com.example.library.models.enums.SearchAlgorithm;
import com.example.library.services.ReadLibraryItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Tag(name = "Read Items", description = "Endpoints for READ operation on library items")
public class ReadLibraryItemController {

    private final ReadLibraryItemService readLibraryItemService;

    @Operation(summary = "Get a specific item by ID")
    @GetMapping("/{id}")
    public ResponseEntity<LibraryItem> getLibraryItemById(@PathVariable int id) {
        LibraryItem item = readLibraryItemService.getLibraryItemById(id);
        return ResponseEntity.ok(item);
    }

    @Operation(summary = "Get all library items")
    @GetMapping
    public ResponseEntity<List<LibraryItem>> getAllLibraryItems() {
        List<LibraryItem> items = readLibraryItemService.getAllLibraryItems();
        return ResponseEntity.ok(items);
    }

    @Operation(summary = "Search library items")
    @GetMapping("/search")
    public ResponseEntity<List<LibraryItem>> searchLibraryItems(
            @RequestParam SearchAlgorithm algorithm,
            @RequestParam String keyword) {

        List<LibraryItem> results = readLibraryItemService.searchLibraryItems(algorithm, keyword);
        return ResponseEntity.ok(results);
    }

    @Operation(summary = "Get items sorted by publication date")
    @GetMapping("/sorted")
    public ResponseEntity<List<LibraryItem>> sortLibraryItemsByPublishDate() {
        List<LibraryItem> sortedItems = readLibraryItemService.sortLibraryItemsByPublishDate();
        return ResponseEntity.ok(sortedItems);
    }
}