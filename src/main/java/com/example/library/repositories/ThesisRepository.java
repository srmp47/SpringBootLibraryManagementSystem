package com.example.library.repositories;

import com.example.library.models.Thesis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThesisRepository extends JpaRepository<Thesis, Integer> {

    List<Thesis> findByUniversity(String university);

    List<Thesis> findByDepartment(String department);

    List<Thesis> findByAdvisor(String advisor);

    List<Thesis> findByUniversityAndDepartment(String university, String department);
}