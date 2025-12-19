package com.example.library.services;

import com.example.library.LibraryManagementSystemApplication;
import com.example.library.database.Database;
import com.example.library.models.LibraryItem;
import com.example.library.models.enums.SearchAlgorithm;
import com.example.library.services.search.strategies.SearchStrategyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class ReadLibraryItemService {
    public LibraryItem getLibraryItemById(int id){
        if(!Database.getLibraryItems().contains(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }
        return Database.getLibraryItems().get(id);
    }

    public ArrayList<LibraryItem> getAllLibraryItems(){
        return Database.getLibraryItems();
    }

    public ArrayList<LibraryItem> searchLibraryItems(SearchAlgorithm searchAlgorithm, String keyword){
        var allItems = Database.getLibraryItems();
        var strategy = SearchStrategyFactory.createStrategy(searchAlgorithm);
        return allItems.parallelStream()
                .filter(item -> strategy.matches(item, keyword))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<LibraryItem> sortLibraryItemsByPublishDate(){
        Database.getLibraryItems().sort(Comparator.comparing(LibraryItem::getPublishDate).reversed());
        return Database.getLibraryItems();
    }

}
