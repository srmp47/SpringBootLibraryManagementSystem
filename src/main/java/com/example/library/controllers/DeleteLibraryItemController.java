package com.example.library.controllers;

import com.example.library.database.Database;
import com.example.library.models.LibraryItem;
import com.example.library.services.DeleteLibraryItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delete")
@RequiredArgsConstructor
@Tag(name = "Delete an Item", description = "Endpoints for DELETE operation on library items")
public class DeleteLibraryItemController {
    private final DeleteLibraryItemService deleteLibraryItemService;

    @Operation(summary = "Delete library item")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLibraryItem(@PathVariable int id){
        deleteLibraryItemService.deleteLibraryItem(id);
    }

}
