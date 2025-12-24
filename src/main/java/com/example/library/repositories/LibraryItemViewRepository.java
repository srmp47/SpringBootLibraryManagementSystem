package com.example.library.repositories;

import com.example.library.models.LibraryItemView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryItemViewRepository extends JpaRepository<LibraryItemView, Integer> {

    @Query(value = "SELECT * FROM library_item_view WHERE borrowed_by IS NOT NULL",
            nativeQuery = true)
    List<LibraryItemView> findBorrowedItems();

    @Query(value = "SELECT * FROM library_item_view WHERE status = 'EXIST'",
            nativeQuery = true)
    List<LibraryItemView> findAvailableItems();

    @Query(value = "SELECT * FROM library_item_view WHERE borrowed_by = :username",
            nativeQuery = true)
    List<LibraryItemView> findItemsBorrowedByUser(String username);
}