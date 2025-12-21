package com.example.library.services;

import com.example.library.exceptions.ResourceNotFoundException;
import com.example.library.repositories.LibraryItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteLibraryItemService {

    private final LibraryItemRepository libraryItemRepository;

    @Transactional
    public void deleteLibraryItem(int id) {
        if (!libraryItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Item not found with id: " + id);
        }
        libraryItemRepository.deleteById(id);
        log.info("Deleted library item with ID: {}", id);
    }
}