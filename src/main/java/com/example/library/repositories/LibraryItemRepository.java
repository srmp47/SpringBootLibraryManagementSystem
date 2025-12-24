package com.example.library.repositories;

import com.example.library.models.LibraryItem;
import com.example.library.models.enums.LibraryItemStatus;
import com.example.library.models.enums.LibraryItemType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryItemRepository extends JpaRepository<LibraryItem, Integer> {

    List<LibraryItem> findByType(LibraryItemType type);

    LibraryItem findById(LibraryItem id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT li FROM LibraryItem li WHERE li.id = :id")
    Optional<LibraryItem> findByIdWithLock(@Param("id") Integer id);

    List<LibraryItem> findByStatus(LibraryItemStatus status);

    List<LibraryItem> findByAuthorContainingIgnoreCase(String author);

    List<LibraryItem> findByTitleContainingIgnoreCase(String title);

    List<LibraryItem> findByPublishDateBetween(LocalDate startDate, LocalDate endDate);

    List<LibraryItem> findByStatusAndUserIsNull(LibraryItemStatus status);

    @Query("SELECT li FROM LibraryItem li WHERE li.user.id = :userId")
    List<LibraryItem> findItemsByUserId(@Param("userId") Integer userId);

    @Query("SELECT li FROM LibraryItem li WHERE LOWER(li.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(li.author) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<LibraryItem> searchByTitleOrAuthor(@Param("keyword") String keyword);

    List<LibraryItem> findAllByOrderByPublishDateDesc();

    boolean existsById(Integer id);

    @Query("SELECT b FROM Book b WHERE b.isbn = :isbn")
    Optional<LibraryItem> findByIsbn(@Param("isbn") String isbn);
}