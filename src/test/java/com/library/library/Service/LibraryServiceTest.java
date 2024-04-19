package com.library.library.Service;

import com.library.library.Entities.*;
import com.library.library.Repository.*;
import com.library.library.Request.CreationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class LibraryServiceTest {
    @Mock
    BooksRepo booksRepo;

    @Mock
    AuthorsRepo authorsRepo;

    @Mock
    GenreRepo genreRepo;

    @Mock
    PublisherRepo publisherRepo;
    @Mock
    MembersRepo membersRepo;

    @InjectMocks
    LibraryService libraryService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        List<Books> booksList = new ArrayList<>();
        Books books = new Books();
        books.setBookId("1234567890");
        books.setTitle("Hello World");
        books.setIsbn("633-65-32");
        books.setPublishingYear(2001);
        books.setQuantity(10);

        Genre genre = new Genre();
        genre.setGenreName("Fiction");
        genre.setGenreId("2781");
        genre.setBooks(booksList);

        Publisher publisher = new Publisher();
        publisher.setPublisherName("Moiz");
        publisher.setPublisherId("165");

        Set<Books> booksSet = new HashSet<>();

        Authors authors = new Authors();
        authors.setAuthorName("Hello");
        authors.setAuthorId("123");
        authors.setBooksSet(booksSet);
        Set<Authors> authorsSet = new HashSet<>();
        authorsSet.add(authors);

        books.setGenre(genre);
        books.setPublisher(publisher);
        books.setAuthorsSet(authorsSet);

        when(genreRepo.save(genre)).thenReturn(genre);
        when(publisherRepo.save(publisher)).thenReturn(publisher);
        when(authorsRepo.save(authors)).thenReturn(authors);
        when(booksRepo.save(books)).thenReturn(books);


        CreationRequest creationRequest = new CreationRequest();
        creationRequest.setIsbn("236-281-23");
        creationRequest.setBookId("12");
        creationRequest.setTitle("Hello World");
        creationRequest.setPublicationYear(2001);
        creationRequest.setQuantity(7);
        creationRequest.setGenre(genre);
        creationRequest.setAuthors(authors);
        creationRequest.setPublisher(publisher);

        CreationRequest creationRequest1 = libraryService.create(creationRequest);

        assertEquals(creationRequest,creationRequest1);

    }

    @Test
    void createMem() {
        Members members = new Members("289","Moiz","Hyd",
                5678098,"moiz@10");

        when(membersRepo.save(any(Members.class))).thenReturn(members);

        Members members1 = libraryService.createMem(members);

        assertEquals(members,members1);

    }

    @Test
    void getAllBooks() {

    }

    @Test
    void getByGenreName() {
    }

    @Test
    void findByBook() {
    }

    @Test
    void getAllMem() {
    }
}