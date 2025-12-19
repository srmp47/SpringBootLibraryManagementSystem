package com.example.library.services.search.strategies;

import com.example.library.models.LibraryItem;

public interface SearchStrategy {
    boolean matches(LibraryItem item, String searchTerm);
}