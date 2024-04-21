package com.library.library.Service;

import com.library.library.Entities.*;
import com.library.library.Exception.IdNotFoundException;
import com.library.library.Repository.*;
import com.library.library.Request.CreationRequest;
import com.sun.source.tree.LambdaExpressionTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

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
        creationRequest.setIsbn(books.getIsbn());
        creationRequest.setBookId(books.getBookId());
        creationRequest.setTitle(books.getTitle());
        creationRequest.setPublicationYear(books.getPublishingYear());
        creationRequest.setQuantity(books.getQuantity());
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

        ResponseEntity<Object> members1 = libraryService.createMem(members);

        assertEquals(HttpStatus.CREATED,members1.getStatusCode());

    }

    @Test
    void getAllBooks() {
        Books books = new Books();
        books.setBookId("1");
        books.setTitle("Farm");
        books.setIsbn("233-3215-5");
        books.setQuantity(5);

        List<Books> booksList = new ArrayList<>();
        booksList.add(books);

        when(booksRepo.findAll()).thenReturn(booksList);

        ResponseEntity<Object> response1 = libraryService.getAllBooks();
        assertEquals(HttpStatus.OK,response1.getStatusCode());

    }

    @Test
    void testGetByGenreId() throws IdNotFoundException {
        Genre genre = Mockito.mock(Genre.class);
        genre.setGenreId("1");
        genre.setGenreName("fiction");

        Books books = new Books();
        books.setGenre(genre);
        books.setTitle("hello");
        books.setPublishingYear(3002);
        List<Books> booksList = new ArrayList<>();

        when(genreRepo.findById(genre.getGenreId())).thenReturn(Optional.of(genre));
        when(genre.getBooks()).thenReturn(booksList);

        ResponseEntity<Object> response = libraryService.getByGenre(genre.getGenreId());
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(booksList,response.getBody());
    }

    @Test
    void getByGenreName() {
        Genre genre = Mockito.mock(Genre.class);
        genre.setGenreName("Fiction");
        genre.setGenreId("2L");

        Books books = new Books();
        books.setBookId("1");
        books.setIsbn("878-34-2123");
        books.setQuantity(4);
        books.setTitle("HELLOWORLD");
        books.setGenre(genre);
        List<Books> booksList = new ArrayList<>();
        booksList.add(books);

        when(genreRepo.findBygenreName(genre.getGenreName())).thenReturn(Optional.of(genre));
        when(genre.getBooks()).thenReturn(booksList);

        ResponseEntity<Object> response = libraryService.getByGenreName(genre.getGenreName());
        assertEquals(HttpStatus.OK,response.getStatusCode());
        List<Books> list = (List<Books>) response.getBody();
        assertNotNull(list);
        Books books1 = list.get(0);
        assertEquals(books.getBookId(),books1.getBookId());
    }

    @Test
    void getAllMem() {
        List<Members> membersList = new ArrayList<>();
        Members members = new Members();
        members.setName("Moiz");
        members.setPhoneNumber(345678);
        members.setAddress("HYD");
        members.setEmail("moiz@10");

        membersList.add(members);

        when(membersRepo.findAll()).thenReturn(membersList);
        ResponseEntity<Object> response = libraryService.getAllMem();
        assertEquals(HttpStatus.OK,response.getStatusCode());

        List<Members> members1 = (List<Members>) response.getBody();
        assert members1 != null;
        assertEquals(membersList.get(0).getMemberId(),members1.get(0).getMemberId());
    }
}