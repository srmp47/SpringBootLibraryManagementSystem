package com.example.library.controllers;

import com.example.library.models.LibraryItem;
import com.example.library.models.User;
import com.example.library.models.enums.LibraryItemStatus;
import com.example.library.models.enums.SearchAlgorithm;
import com.example.library.services.DeleteLibraryItemService;
import com.example.library.services.ReadLibraryItemService;
import com.example.library.services.UpdateLibraryItemService;
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

    @GetMapping
    public ResponseEntity<List<LibraryItem>> getAll() {
        return ResponseEntity.ok(readLibraryItemService.getAllLibraryItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryItem> getById(@PathVariable int id) {
        return ResponseEntity.ok(readLibraryItemService.getLibraryItemById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<LibraryItem>> search(
            @RequestParam SearchAlgorithm algorithm,
            @RequestParam String keyword) {
        return ResponseEntity.ok(
                readLibraryItemService.searchLibraryItems(algorithm, keyword)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable int id,
            @AuthenticationPrincipal User currentUser) {
        deleteLibraryItemService.deleteLibraryItem(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{itemId}/borrow")
    public ResponseEntity<LibraryItem> borrowItem(
            @PathVariable int itemId,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(
                updateLibraryItemService.borrowItem(itemId, currentUser.getId())
        );
    }

    @PatchMapping("/{itemId}/return")
    public ResponseEntity<LibraryItem> returnItem(
            @PathVariable int itemId,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(
                updateLibraryItemService.returnItem(itemId, currentUser.getId())
        );
    }

    @PatchMapping("/{id}/status/{newStatus}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<LibraryItem> updateStatus(
            @PathVariable int id,
            @PathVariable LibraryItemStatus newStatus) {
        return ResponseEntity.ok(
                updateLibraryItemService.patchLibraryItemStatus(id, newStatus)
        );
    }
}
