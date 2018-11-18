package com.example.library.controller;

import com.example.library.dto.AuthorResponseDTO;
import com.example.library.service.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    private final IAuthorService authorService;

    @Autowired
    public AuthorController(IAuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/pages")
    public Long getCount() {
        return authorService.getPagesCount();
    }

    @GetMapping("/pages/{letter}")
    public Long getCount(@PathVariable("letter")String letter) { return authorService.getPagesCount(letter);
    }

    @GetMapping("/get/{page}")
    public List<AuthorResponseDTO> getAuthors(@PathVariable("page") int page) {
        return authorService.getAuthors(page);
    }

    @GetMapping("/get/{letter}/{page}")
    public List<AuthorResponseDTO> getAuthors(@PathVariable("letter")String letter, @PathVariable("page")int page) {
        return authorService.getAuthors(letter,page);
    }

}
