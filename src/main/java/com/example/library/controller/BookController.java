package com.example.library.controller;

import com.example.library.dto.AuthorDTO;
import com.example.library.dto.BookDTO;
import com.example.library.dto.BookResponseDTO;
import com.example.library.model.Genre;
import com.example.library.service.IAuthorService;
import com.example.library.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private static final String SUCCESS_MESSAGE = "The book has been successfully added!";
    private final IBookService bookService;
    private final IAuthorService authorService;
    private final ServletContext servletContext;

    @Autowired
    public BookController(IBookService bookService, IAuthorService authorService, ServletContext servletContext) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.servletContext = servletContext;
    }

    @PostMapping("/add")
    public ResponseEntity addBook(@Valid @RequestPart("authorDTO") AuthorDTO authorDTO,
                                  @Valid @RequestPart("bookDTO") BookDTO bookDTO,
                                  @Valid @NotNull @RequestPart("book") MultipartFile book,
                                  BindingResult result, @Nullable @RequestPart("image") MultipartFile image
    ) throws IOException {
        if (result.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        }
        bookService.addBook(bookDTO, authorService.addAuthor(authorDTO), book, image);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @GetMapping("/pages")
    public Long getCount() {
        return bookService.getPagesCount();
    }

    @GetMapping("/pages/{genre}")
    public Long getCount(@PathVariable("genre") Genre genre) {
        return bookService.getPagesCount(genre);
    }

    @GetMapping("/count/{title}")
    public Long getCount(@PathVariable("title") String title) {
        return bookService.getPagesCount(title);
    }

    @GetMapping("/get/{page}")
    public List<BookResponseDTO> getAuthors(@PathVariable("page") int page) {
        return bookService.getBooks(page);
    }

    @GetMapping("/get/{genre}/{page}")
    public List<BookResponseDTO> getAuthors(@PathVariable("genre") Genre genre, @PathVariable("page") int page) {
        return bookService.getBooks(genre, page);
    }

    @GetMapping("/search/{text}/{page}")
    public List<BookResponseDTO> searchBook(@PathVariable("text") String keyword,@PathVariable("page") int page) {
        return bookService.search(keyword,page);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> download(@PathVariable("id") Long id) throws IOException {
        File file=new File(bookService.getFilename(id));
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.parseMediaType(servletContext.getMimeType(file.getName())))
                .contentLength(file.length()) //
                .body(resource);
    }
}
