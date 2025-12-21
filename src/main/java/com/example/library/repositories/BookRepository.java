package com.example.library.repositories;

import com.example.library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Optional<Book> findByIsbn(String isbn);

    List<Book> findByGenre(String genre);

    List<Book> findByPageCountBetween(int minPages, int maxPages);

    List<Book> findByAuthorAndGenre(String author, String genre);

    @Query("SELECT b FROM Book b WHERE " +
            "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))) AND " +
            "(:genre IS NULL OR b.genre = :genre)")
    List<Book> searchBooks(@Param("title") String title,
                           @Param("author") String author,
                           @Param("genre") String genre);

    boolean existsByIsbn(String isbn);
}