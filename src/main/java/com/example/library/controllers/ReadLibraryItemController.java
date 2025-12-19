package com.example.library.controllers;

import com.example.library.models.LibraryItem;
import com.example.library.models.enums.SearchAlgorithm;
import com.example.library.services.ReadLibraryItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/get")
@RequiredArgsConstructor
@Tag(name = "Read Items", description = "Endpoints for READ operation on library items")
public class ReadLibraryItemController {
    private final ReadLibraryItemService readLibraryItemService;

    @Operation(summary = "Get one item")
    @GetMapping("/{id}")
    public LibraryItem getLibraryItemById(@PathVariable int id){
        return readLibraryItemService.getLibraryItemById(id);
    }

    @Operation(summary = "Get all library items")
    @GetMapping("")
    public ArrayList<LibraryItem> getAllLibraryItems(){
        return readLibraryItemService.getAllLibraryItems();
    }

    @Operation(summary = "Search in items")
    @GetMapping("/search/{algorithm}/{keyword}")
    public ArrayList<LibraryItem> searchLibraryItems(@Valid @PathVariable SearchAlgorithm algorithm,
                                                     @PathVariable String keyword, ServletRequest servletRequest){
        return readLibraryItemService.searchLibraryItems(algorithm, keyword);
    }

    @Operation(summary = "Sort items by publication date")
    @GetMapping("/sort")
    public ArrayList<LibraryItem> sortLibraryItemsByPublishDate(){
        return readLibraryItemService.sortLibraryItemsByPublishDate();
    }

}
