package com.example.library.controllers;

import com.example.library.models.LibraryItem;
import com.example.library.models.enums.LibraryItemStatus;
import com.example.library.models.enums.SearchAlgorithm;
import com.example.library.services.ReadLibraryItemService;
import com.example.library.services.UpdateLibraryItemService;
import com.example.library.services.DeleteLibraryItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Tag(name = "Library Items", description = "Core operations for all library items")
public class LibraryItemController {

    private final ReadLibraryItemService readLibraryItemService;
    private final UpdateLibraryItemService updateLibraryItemService;
    private final DeleteLibraryItemService deleteLibraryItemService;

    @Operation(summary = "Get all library items", description = "Retrieves a list of all items available in the library system")
    @GetMapping
    public ResponseEntity<List<LibraryItem>> getAll() {
        return ResponseEntity.ok(readLibraryItemService.getAllLibraryItems());
    }

    @Operation(summary = "Get a specific item by ID", description = "Fetch detailed information of a single library item using its unique ID")
    @GetMapping("/{id}")
    public ResponseEntity<LibraryItem> getById(@PathVariable int id) {
        return ResponseEntity.ok(readLibraryItemService.getLibraryItemById(id));
    }

    @Operation(summary = "Search library items", description = "Search for items using specific algorithms (Linear/Binary) and keywords")
    @GetMapping("/search")
    public ResponseEntity<List<LibraryItem>> search(@RequestParam SearchAlgorithm algorithm, @RequestParam String keyword) {
        return ResponseEntity.ok(readLibraryItemService.searchLibraryItems(algorithm, keyword));
    }

    @Operation(summary = "Delete an item", description = "Permanently removes a library item from the database by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        deleteLibraryItemService.deleteLibraryItem(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Borrow an item", description = "Assigns an item to a user and updates its status to BORROWED")
    @PatchMapping("/{itemId}/borrow/{userId}")
    public ResponseEntity<LibraryItem> borrowItem(@PathVariable int itemId, @PathVariable int userId) {
        return ResponseEntity.ok(updateLibraryItemService.borrowItem(itemId, userId));
    }

    @Operation(summary = "Return an item", description = "Unassigns the user from the item and sets its status back to AVAILABLE")
    @PatchMapping("/{itemId}/return")
    public ResponseEntity<LibraryItem> returnItem(@PathVariable int itemId) {
        return ResponseEntity.ok(updateLibraryItemService.returnItem(itemId));
    }

    @Operation(summary = "Update item status", description = "Partially updates the status of a library item (e.g., to DAMAGED or LOST)")
    @PatchMapping("/{id}/status/{newStatus}")
    public ResponseEntity<LibraryItem> updateStatus(@PathVariable int id, @PathVariable LibraryItemStatus newStatus) {
        return ResponseEntity.ok(updateLibraryItemService.patchLibraryItemStatus(id, newStatus));
    }
}