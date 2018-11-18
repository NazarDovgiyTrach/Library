package com.example.library;

import com.example.library.dao.IAuthorDAO;
import com.example.library.dao.IBookDAO;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.service.IAuthorService;
import com.example.library.service.IBookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {
    @Autowired
    IAuthorService service;
    @Autowired
    IBookService bookService;
    @MockBean
    IAuthorDAO dao;
    @MockBean
    IBookDAO bookDAO;

    @Test
    public void getPagesCount() {
        when(dao.count()).thenReturn(9L);
        assertThat(2L, equalTo(service.getPagesCount()));
        verify(service, times(1)).getPagesCount();
    }

    @Test
    public void getAuthorsTest() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Taras");
        when(dao.findAllByNameStartingWith("T", PageRequest.of(0, 5))).thenReturn(Arrays.asList(author));
        assertThat(1, equalTo(service.getAuthors("T", 0).size()));
        verify(service, times(1)).getAuthors("T", 0);
    }

    @Test
    public void searchTest() {
        Book book = new Book();
        book.setTitle("Test");
        when(bookDAO.findAllByTitleIgnoreCaseLike("Test")).thenReturn(Arrays.asList(book));
        assertThat("Test", equalTo(bookService.search("Test").get(0).getTitle()));

    }

}
