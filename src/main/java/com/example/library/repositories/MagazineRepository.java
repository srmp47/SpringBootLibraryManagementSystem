package com.example.library.repositories;

import com.example.library.models.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MagazineRepository extends JpaRepository<Magazine, Integer> {

    Optional<Magazine> findByIssueNumber(String issueNumber);

    List<Magazine> findByPublisher(String publisher);

    List<Magazine> findByCategory(String category);

    List<Magazine> findByPublisherAndCategory(String publisher, String category);
}