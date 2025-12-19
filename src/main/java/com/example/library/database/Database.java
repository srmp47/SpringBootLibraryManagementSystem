package com.example.library.database;

import com.example.library.models.LibraryItem;

import java.util.ArrayList;

public class  Database {
    private static ArrayList<LibraryItem> libraryItems = new ArrayList<LibraryItem>();

    public static ArrayList<LibraryItem> getLibraryItems() {
        return libraryItems;
    }
}
