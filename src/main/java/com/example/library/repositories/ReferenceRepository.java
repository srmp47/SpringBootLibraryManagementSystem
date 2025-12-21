package com.example.library.repositories;

import com.example.library.models.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference, Integer> {

    List<Reference> findByReferenceType(String referenceType);

    List<Reference> findBySubject(String subject);

    List<Reference> findByEdition(String edition);

    List<Reference> findBySubjectAndEdition(String subject, String edition);
}