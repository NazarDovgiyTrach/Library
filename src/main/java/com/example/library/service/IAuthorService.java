package com.example.library.service;

import com.example.library.dto.AuthorDTO;
import com.example.library.dto.AuthorResponseDTO;
import com.example.library.model.Author;

import java.util.List;

public interface IAuthorService {
    Author addAuthor(AuthorDTO authorDTO);

    Long getPagesCount();

    Long getPagesCount(String letter);

    List<AuthorResponseDTO> getAuthors(int page);

    List<Author> getAuthors();

    List<AuthorResponseDTO> getAuthors(String letter,int page);
}
