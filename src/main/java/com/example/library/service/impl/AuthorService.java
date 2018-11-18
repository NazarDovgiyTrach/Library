package com.example.library.service.impl;

import com.example.library.dao.IAuthorDAO;
import com.example.library.dto.AuthorDTO;
import com.example.library.dto.AuthorResponseDTO;
import com.example.library.model.Author;
import com.example.library.service.IAuthorService;
import com.example.library.util.FileStorageUtil;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.ceil;

@Service
@Data
public class AuthorService implements IAuthorService {
    private static final Integer MAX_RESULT = 5;
    private final IAuthorDAO dao;
    private final ModelMapper mapper;
    private final FileStorageUtil fileStorageUtil;

    @Override
    public Author addAuthor(AuthorDTO authorDTO) {
        Author author = mapper.map(authorDTO, Author.class);
        return dao.exists(Example.of(author))?dao.findOne(Example.of(author)).get():dao.save(author);

    }

    @Override
    public Long getPagesCount() {
        return (long) ceil((double) dao.count() / MAX_RESULT);
    }

    @Override
    public Long getPagesCount(String letter) {
        return (long) ceil((double) dao.countByNameStartingWith(letter) / MAX_RESULT);
    }

    @Override
    public List<AuthorResponseDTO> getAuthors(int page) {
        return dao.findAll(PageRequest.of(page - 1, MAX_RESULT)).getContent().stream().
                map(v -> (mapper.map(v, AuthorResponseDTO.class))).collect(Collectors.toList());
    }

    public List<Author> getAuthors() {
        return dao.findAll();
    }

    @Override
    public List<AuthorResponseDTO> getAuthors(String letter,int page) {
        return dao.findAllByNameStartingWith(letter,PageRequest.of(page - 1, MAX_RESULT)).stream().
                map(v -> (mapper.map(v, AuthorResponseDTO.class))).collect(Collectors.toList());
    }
}
