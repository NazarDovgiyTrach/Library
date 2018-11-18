package com.example.library.dao;

import com.example.library.model.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAuthorDAO extends JpaRepository<Author, Long> {
    List<Author> findAllByNameStartingWith(String letter, Pageable pageable);

    Long countByNameStartingWith(String letter);
}
