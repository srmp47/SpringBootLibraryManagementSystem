package com.example.library.services;

import com.example.library.LibraryManagementSystemApplication;
import com.example.library.database.Database;
import com.example.library.models.LibraryItem;
import com.example.library.models.enums.LibraryItemStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.plaf.SpinnerUI;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateLibraryItemService {
    @Transactional
    public LibraryItem patchLibraryItemStatus(int id, LibraryItemStatus newStatus){
        LibraryItem libraryItem = Database.getLibraryItems().get(id);
        if(libraryItem == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no item with this id");
        }
        libraryItem.setStatus(newStatus);
        return libraryItem;
    }
}
