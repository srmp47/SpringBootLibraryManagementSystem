package com.example.library.services;

import com.example.library.exceptions.ResourceNotFoundException;
import com.example.library.models.LibraryItem;
import com.example.library.models.enums.SearchAlgorithm;
import com.example.library.repositories.LibraryItemRepository;
import com.example.library.services.search.strategies.SearchStrategyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReadLibraryItemService {

    private final LibraryItemRepository libraryItemRepository;

    public LibraryItem getLibraryItemById(int id) {
        return libraryItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item with id " + id + " not found"));
    }

    public List<LibraryItem> getAllLibraryItems() {
        return libraryItemRepository.findAll();
    }

    public List<LibraryItem> searchLibraryItems(SearchAlgorithm searchAlgorithm, String keyword) {
        var allItems = libraryItemRepository.findAll();
        var strategy = SearchStrategyFactory.createStrategy(searchAlgorithm);
        return allItems.stream()
                .filter(item -> strategy.matches(item, keyword))
                .collect(Collectors.toList());
    }

    public List<LibraryItem> sortLibraryItemsByPublishDate() {
        return libraryItemRepository.findAllByOrderByPublishDateDesc();
    }
}