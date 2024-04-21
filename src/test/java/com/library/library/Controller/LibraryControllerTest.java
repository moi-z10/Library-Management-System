package com.library.library.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.library.library.Entities.*;
import com.library.library.Exception.IdNotFoundException;
import com.library.library.Request.CreationRequest;
import com.library.library.Service.LibraryService;
import com.library.library.Service.TransactionService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest
class LibraryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LibraryService libraryService;
    @MockBean
    TransactionService transactionService;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Test
    void creation() throws Exception {
        Books books = new Books();
        books.setBookId("1");
        books.setTitle("farm");
        books.setPublishingYear(2004);
        books.setQuantity(8);
        books.setIsbn("321-34-212");

        Genre genre = new Genre();
        genre.setGenreName("fiction");
        genre.setGenreId("1");
        books.setGenre(genre);

        Publisher publisher = new Publisher();
        publisher.setPublisherId("1");
        publisher.setPublisherName("Moiz");
        books.setPublisher(publisher);

        Authors authors = new Authors();
        authors.setAuthorName("Elloyd");
        authors.setAuthorId("1");

        CreationRequest creationRequest = new CreationRequest();
        creationRequest.setPublisher(publisher);
        creationRequest.setAuthors(authors);
        creationRequest.setPublicationYear(books.getPublishingYear());
        creationRequest.setIsbn(books.getIsbn());
        creationRequest.setBookId(books.getBookId());

        when(libraryService.create(creationRequest)).thenReturn(creationRequest);
        String content = objectWriter.writeValueAsString(creationRequest);

        mockMvc.perform(post("/library/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void createMembers() throws Exception {
        Members members = new Members();
        members.setEmail("moiz");
        members.setPhoneNumber(987678);
        members.setMemberId("1");
        members.setName("moiz");

        ResponseEntity<Object> responseEntity = ResponseEntity.ok().body(members);
        when(libraryService.createMem(members)).thenReturn(responseEntity);
        String content = objectWriter.writeValueAsString(members);

        mockMvc.perform(post("/library/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getAllBok() throws Exception {
        Books books = new Books();
        books.setBookId("1");
        books.setTitle("Hello");
        books.setQuantity(2);
        books.setIsbn("12-45-32");

        List<Books> list = new ArrayList<>();
        list.add(books);

        ResponseEntity<Object> response = ResponseEntity.ok(list);
        when(libraryService.getAllBooks()).thenReturn(response);

        mockMvc.perform(get("/library/getbooks"))
                .andDo(print()).andExpect(status().isOk());

        ResponseEntity<Object> response1 = ResponseEntity.status(HttpStatus.NO_CONTENT).
                body(Collections.emptyList());
        when(libraryService.getAllBooks()).thenReturn(response1);
        mockMvc.perform(get("/library/getbooks"))
                .andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    void getByGenre() throws Exception {
        Genre genre = new Genre();
        genre.setGenreId("1");
        genre.setGenreName("Fiction");

        Books books = new Books();
        books.setBookId("1");
        books.setTitle("hello");
        books.setGenre(genre);

        ResponseEntity<Object> response = ResponseEntity.ok(books);
        when(libraryService.getByGenre(genre.getGenreId())).thenReturn(response);
        mockMvc.perform(get("/library/gen/"+genre.getGenreId()))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getByGenreName() throws Exception {
        Genre genre = new Genre();
        genre.setGenreId("1");
        genre.setGenreName("Fiction");

        Books books = new Books();
        books.setBookId("1");
        books.setTitle("hello");
        books.setGenre(genre);

        ResponseEntity<Object> response = ResponseEntity.ok(books);
        when(libraryService.getByGenreName(genre.getGenreName())).thenReturn(response);

        mockMvc.perform(get("/library/genname/"+genre.getGenreName()))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getAllMembers() throws Exception {
        Members members = new Members();
        members.setMemberId("1");
        members.setName("Moz");
        members.setAddress("Hyd");

        ResponseEntity<Object> response = ResponseEntity.ok(members);
        when(libraryService.getAllMem()).thenReturn(response);

        mockMvc.perform(get("/library/getmem"))
                .andDo(print()).andExpect(status().isOk());

    }

    @Test
    void borrowBook() {

    }

    @Test
    void getTransactions() {
    }
}