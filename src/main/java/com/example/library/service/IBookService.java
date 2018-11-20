package com.example.library.service;

import com.example.library.dto.BookDTO;
import com.example.library.dto.BookResponseDTO;
import com.example.library.model.Author;
import com.example.library.model.Genre;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IBookService {
    void addBook(BookDTO bookDTO, Author author, MultipartFile book, MultipartFile img) throws IOException;

    Long getPagesCount();

    Long getPagesCount(Genre genre);

    Long getPagesCount(String title);

    List<BookResponseDTO> getBooks(int page);

    List<BookResponseDTO> getBooks(Genre genre, int page);

    List<BookResponseDTO> search(String keyword,int page);

    String getFilename(Long id);
}
