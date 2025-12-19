package com.example.library.services.search.strategies.impl;

import com.example.library.models.LibraryItem;
import com.example.library.services.search.strategies.SearchStrategy;

public class AuthorIgnoreCaseSearch implements SearchStrategy {
    @Override
    public boolean matches(LibraryItem item, String searchTerm) {
        return item.getAuthor().toLowerCase().contains(searchTerm.toLowerCase());
    }
}