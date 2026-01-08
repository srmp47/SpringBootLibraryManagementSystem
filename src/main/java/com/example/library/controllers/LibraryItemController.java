package com.example.library.controllers;

import com.example.library.models.LibraryItem;
import com.example.library.models.User;
import com.example.library.models.enums.LibraryItemStatus;
import com.example.library.models.enums.SearchAlgorithm;
import com.example.library.services.MonitoringService;
import com.example.library.services.ReadLibraryItemService;
import com.example.library.services.UpdateLibraryItemService;
import com.example.library.services.DeleteLibraryItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final MonitoringService monitoringService;

    @Operation(summary = "Get all library items", description = "Retrieves a list of all items available in the library system")
    @GetMapping
    public ResponseEntity<List<LibraryItem>> getAll() {
        monitoringService.incrementTotal();
        monitoringService.incrementSuccess();
        return ResponseEntity.ok(readLibraryItemService.getAllLibraryItems());
    }

    @Operation(summary = "Get a specific item by ID", description = "Fetch detailed information of a single library item using its unique ID")
    @GetMapping("/{id}")
    public ResponseEntity<LibraryItem> getById(@PathVariable int id) {
        monitoringService.incrementTotal();
        monitoringService.incrementSuccess();
        return ResponseEntity.ok(readLibraryItemService.getLibraryItemById(id));
    }

    @Operation(summary = "Search library items", description = "Search for items using specific algorithms (Linear/Binary) and keywords")
    @GetMapping("/search")
    public ResponseEntity<List<LibraryItem>> search(@RequestParam SearchAlgorithm algorithm, @RequestParam String keyword) {
        monitoringService.incrementTotal();
        monitoringService.incrementSuccess();
        return ResponseEntity.ok(readLibraryItemService.searchLibraryItems(algorithm, keyword));
    }

    @Operation(summary = "Delete an item", description = "Permanently removes a library item from the database by its ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        monitoringService.incrementTotal();
        deleteLibraryItemService.deleteLibraryItem(id);
        monitoringService.incrementSuccess();
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Borrow an item", description = "Assigns an item to the current authenticated user")
    @PatchMapping("/{itemId}/borrow")
    public ResponseEntity<LibraryItem> borrowItem( @PathVariable int itemId, @AuthenticationPrincipal User currentUser){
        monitoringService.incrementTotal();
        monitoringService.incrementSuccess();
        return ResponseEntity.ok(updateLibraryItemService.borrowItem(itemId, currentUser.getId()));
    }

    @Operation(summary = "Return an item", description = "Unassigns the user from the item and sets its status back to AVAILABLE")
    @PatchMapping("/{itemId}/return")
    public ResponseEntity<LibraryItem> returnItem(@PathVariable int itemId, @AuthenticationPrincipal User currentUser) {
        monitoringService.incrementTotal();
        monitoringService.incrementSuccess();
        return ResponseEntity.ok(updateLibraryItemService.returnItem(itemId, currentUser.getId()));
    }

    @Operation(summary = "Update item status", description = "Partially updates the status of a library item (e.g., to DAMAGED or LOST)")
    @PatchMapping("/{id}/status/{newStatus}")
    public ResponseEntity<LibraryItem> updateStatus(@PathVariable int id, @PathVariable LibraryItemStatus newStatus) {
        monitoringService.incrementTotal();
        monitoringService.incrementSuccess();
        return ResponseEntity.ok(updateLibraryItemService.patchLibraryItemStatus(id, newStatus));
    }
}