package com.example.library.dao;

import com.example.library.model.Book;
import com.example.library.model.Genre;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookDAO extends JpaRepository<Book, Long> {

    List<Book> findByGenre(Genre genre, Pageable pageable);

    Long countByGenre(Genre genre);

    List<Book> findAllByTitleIgnoreCaseLike(String keyword);

    @Query("SELECT b.filename FROM Book b where b.id = :id")
    String findFilenameById(@Param("id") Long id);
}
