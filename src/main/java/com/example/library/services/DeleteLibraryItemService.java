package com.example.library.services;

import com.example.library.LibraryManagementSystemApplication;
import com.example.library.database.Database;
import com.example.library.models.LibraryItem;
import com.example.library.models.LibraryResult;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteLibraryItemService {
    @Transactional
    public void deleteLibraryItem(int id){
        if(!Database.getLibraryItems().contains(id)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asd");
        }
        Database.getLibraryItems().remove(id);
    }
}
