package com.library.library.Service;

import com.library.library.DTO.TransactionsDTO;
import com.library.library.Entities.Books;
import com.library.library.Entities.Members;
import com.library.library.Entities.Transactions;
import com.library.library.Exception.IdNotFoundException;
import com.library.library.Repository.BooksRepo;
import com.library.library.Repository.MembersRepo;
import com.library.library.Repository.TransactionsRepo;
import com.library.library.Request.TransactionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.awt.print.Book;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TransactionServiceTest {

    @Mock
    TransactionsRepo transactionsRepo;
    @Mock
    MembersRepo membersRepo;
    @Mock
    BooksRepo booksRepo;
    @InjectMocks
    TransactionService transactionService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void borrow() throws IdNotFoundException {
        Books books = new Books();
        books.setBookId("1");
        books.setTitle("Hello world");
        books.setQuantity(1);

        Members members = new Members();
        members.setMemberId("1");
        members.setAddress("HYD");

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setTransactionType("borrow");
        transactionRequest.setBookId(books.getBookId());
        transactionRequest.setMemberId(members.getMemberId());

        when(transactionsRepo.findByMembersAndBooks(members,books)).thenReturn(Optional.of
                (new Transactions()));
        ResponseEntity<Object> response1= transactionService.borrow(transactionRequest);
        assertEquals(HttpStatus.NOT_FOUND,response1.getStatusCode());


        when(transactionsRepo.findByMembersAndBooks(any(Members.class), any(Books.class)))
                .thenReturn(Optional.empty());
        when(booksRepo.findById(books.getBookId())).thenReturn(Optional.of(books));
        when(membersRepo.findById(members.getMemberId())).thenReturn(Optional.of(members));
        when(transactionsRepo.save(any(Transactions.class))).thenReturn(new Transactions());

        ResponseEntity<Object> response = transactionService.borrow(transactionRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void returnBook() {
        Books books = new Books();
        books.setBookId("1");
        books.setTitle("Book Title");
        books.setIsbn("123-456-789");
        books.setQuantity(10);

        Members members = new Members();
        members.setMemberId("1");
        members.setName("John Doe");
        members.setEmail("john@example.com");
        members.setPhoneNumber(9876789);

        Transactions transactions = new Transactions();
        transactions.setTransactionId("1");
        transactions.setBooks(books);
        transactions.setMembers(members);
        transactions.setTransactionType("return");
        transactions.setDueDate(new Date(System.currentTimeMillis()));
        when(booksRepo.save(books)).thenReturn(books);


        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setBookId(books.getBookId());
        transactionRequest.setMemberId(members.getMemberId());
        transactionRequest.setTransactionType("return");

        when(transactionsRepo.findByMembersAndBooks(any(Members.class),any(Books.class))).
                thenReturn(Optional.empty());
        when(booksRepo.findById(books.getBookId())).thenReturn(Optional.of(books));
        when(membersRepo.findById(members.getMemberId())).thenReturn(Optional.of(members));

        ResponseEntity<Object> response = transactionService.returnBook(transactionRequest);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());

        when(transactionsRepo.findByMembersAndBooks(any(Members.class), any(Books.class)))
                .thenReturn(Optional.of(transactions));
        when(booksRepo.findById(books.getBookId())).thenReturn(Optional.of(books));
        when(membersRepo.findById(members.getMemberId())).thenReturn(Optional.of(members));
        when(transactionsRepo.save(transactions)).thenReturn(transactions);


        ResponseEntity<Object> response1 = transactionService.returnBook(transactionRequest);
        assertEquals(HttpStatus.OK, response1.getStatusCode());
    }

    @Test
    void getAllTransactions() {
        Books books = new Books();
        books.setBookId("1");
        Members members = new Members();
        members.setMemberId("1");
        Transactions transactions = new Transactions();
        transactions.setTransactionId("1");
        transactions.setMembers(members);
        transactions.setBooks(books);
        List<Transactions> list = new ArrayList<>();
        list.add(transactions);

        when(transactionsRepo.findAll()).thenReturn(Collections.emptyList());
        ResponseEntity<Object> response = transactionService.getAllTransactions();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        when(transactionsRepo.findAll()).thenReturn(list);
        ResponseEntity<Object> response1 = transactionService.getAllTransactions();
        assertEquals(HttpStatus.OK,response1.getStatusCode());
    }

    @Test
    void getByBookId() {
        Books books = new Books();
        books.setBookId("1");
        Members members = new Members();
        members.setMemberId("1");

        Transactions transactions = new Transactions();
        transactions.setTransactionId("1");
        transactions.setMembers(members);
        transactions.setBooks(books);
        List<Transactions> list = new ArrayList<>();
        list.add(transactions);
        when(transactionsRepo.findByBooksBookId(books.getBookId())).thenReturn(list);

        ResponseEntity<Object> response = transactionService.getByBookId(books.getBookId());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    void getByMemberId() {
        Books books = new Books();
        books.setBookId("1");
        Members members = new Members();
        members.setMemberId("1");

        Transactions transactions = new Transactions();
        transactions.setTransactionId("1");
        transactions.setMembers(members);
        transactions.setBooks(books);

        List<Transactions> list = new ArrayList<>();
        list.add(transactions);
        when(transactionsRepo.findByMembersMemberId(members.getMemberId())).thenReturn(list);
        ResponseEntity<Object> response = transactionService.getByMemberId(members.getMemberId());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    void getByMemberAndBooks() {
        Books books = new Books();
        books.setBookId("1");
        Members members = new Members();
        members.setMemberId("1");

        Transactions transactions = new Transactions();
        transactions.setTransactionId("1");
        transactions.setMembers(members);
        transactions.setBooks(books);

        List<Transactions> list = new ArrayList<>();
        list.add(transactions);
        when(transactionsRepo.findByMembers_MemberIdAndBooks_BookId(members.getMemberId(),
                books.getBookId())).thenReturn(list);
        ResponseEntity<Object> response = transactionService.getByMemberAndBooks
                (members.getMemberId(),books.getBookId());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }
}