package com.library.library.Repository;

import com.library.library.Entities.Books;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BooksRepoTest {

    @Test
    void findByTitle() {

        BooksRepo booksRepo = Mockito.mock(BooksRepo.class);
        Books books = new Books();
        books.setBookId("1");
        books.setTitle("hello world");
        books.setQuantity(10);

        when(booksRepo.findByTitle(books.getTitle())).thenReturn(Optional.of(books));
        Optional<Books> books1= booksRepo.findByTitle(books.getTitle());

        assertNotNull(books1);
        assertEquals(books,books1.get());
    }
}