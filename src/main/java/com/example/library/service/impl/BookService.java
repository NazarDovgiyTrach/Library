package com.example.library.service.impl;

import com.example.library.dao.IBookDAO;
import com.example.library.dto.BookDTO;
import com.example.library.dto.BookResponseDTO;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.Genre;
import com.example.library.service.IBookService;
import com.example.library.util.FileStorageUtil;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.ceil;

@Service
@Data
public class BookService implements IBookService {
    private static final Integer MAX_RESULT = 5;
    @Value("${upload.books}")
    private String uploadBooks;
    @Value("${upload.images}")
    private String uploadImg;
    private final IBookDAO dao;
    private final FileStorageUtil fileStorageUtil;
    private final ModelMapper mapper;

    @Override
    public void addBook(BookDTO bookDTO, Author author, MultipartFile book, MultipartFile img) throws IOException {

            Book entity = mapper.map(bookDTO, Book.class);
            entity.setFilename(fileStorageUtil.saveFile(book, uploadBooks));
            entity.setImagePath(fileStorageUtil.saveFile(img, uploadImg));
            entity.setAuthor(author);
            dao.save(entity);


    }

    @Override
    public Long getPagesCount() {
        return (long) ceil((double) dao.count() / MAX_RESULT);
    }

    @Override
    public Long getPagesCount(Genre genre) {
        return (long) ceil((double) dao.countByGenre(genre) / MAX_RESULT);
    }

    @Override
    public Long getPagesCount(String title) {
        return (long) ceil((double) dao.countByTitleIgnoreCaseLike(title) / MAX_RESULT);
    }

    @Override
    public List<BookResponseDTO> getBooks(int page) {
        return dao.findAll(PageRequest.of(page - 1, MAX_RESULT)).getContent().stream().
                map(v -> (mapper.map(v, BookResponseDTO.class))).collect(Collectors.toList());
    }
    @Override
    public List<BookResponseDTO> getBooks(Genre genre, int page) {
        return dao.findByGenre(genre, PageRequest.of(page - 1, MAX_RESULT)).stream().
                map(v -> (mapper.map(v, BookResponseDTO.class))).collect(Collectors.toList());
    }

    @Override
    public List<BookResponseDTO> search(String keyword,int page) {
        return dao.findAllByTitleIgnoreCaseLike(keyword,PageRequest.of(page - 1, MAX_RESULT)).stream().
                map(v -> (mapper.map(v, BookResponseDTO.class))).collect(Collectors.toList());
    }

    @Override
    public String getFilename(Long id) {
        return dao.findFilenameById(id);
    }
}
