package com.library.library.Repository;

import com.library.library.Entities.Books;
import com.library.library.Entities.Members;
import com.library.library.Entities.Transactions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TransactionsRepoTest {

    @Test
    void findByMembersAndBooks() {
        TransactionsRepo transactionsRepo = Mockito.mock(TransactionsRepo.class);
        Members members = new Members();
        members.setMemberId("1");
        Books books= new Books();
        books.setBookId("1");

        Transactions transactions1 = new Transactions();

        when(transactionsRepo.findByMembersAndBooks(members,books)).thenReturn(
                Optional.of(transactions1));
        Optional<Transactions> transactions = transactionsRepo.findByMembersAndBooks(members,books);

        assertEquals(transactions1,transactions.get());

    }

    @Test
    void findByBooksBookId() {
        TransactionsRepo transactionsRepo = Mockito.mock(TransactionsRepo.class);

        Books books = new Books();
        books.setBookId("1");

        Transactions transactions = new Transactions();
        List<Transactions> list = new ArrayList<>();
        list.add(transactions);
        when(transactionsRepo.findByBooksBookId(books.getBookId())).thenReturn(list);

        List<Transactions> transactions1 = transactionsRepo.findByBooksBookId(books.getBookId());
        assertEquals(list.size(),transactions1.size());
        assertEquals(list,transactions1);
    }

    @Test
    void findByMembersMemberId() {
        TransactionsRepo transactionsRepo = Mockito.mock(TransactionsRepo.class);
        Members members = new Members();
        members.setMemberId("1");
        Transactions transactions = new Transactions();
        List<Transactions> list = new ArrayList<>();
        list.add(transactions);

        when(transactionsRepo.findByMembersMemberId(members.getMemberId())).thenReturn(list);
        List<Transactions> transactions1 = transactionsRepo.findByMembersMemberId(
                members.getMemberId());
        assertEquals(list.size(),transactions1.size());
        assertEquals(list,transactions1);

    }

    @Test
    void findByMembers_MemberIdAndBooks_BookId() {
        TransactionsRepo transactionsRepo = Mockito.mock(TransactionsRepo.class);

        Books books = new Books();
        books.setBookId("1");
        Members members = new Members();
        members.setMemberId("1");
        Transactions transactions = new Transactions();
        List<Transactions> list = new ArrayList<>();
        list.add(transactions);

        when(transactionsRepo.findByMembers_MemberIdAndBooks_BookId(
                members.getMemberId(),books.getBookId())).thenReturn(list);
        List<Transactions> transactions1 = transactionsRepo.findByMembers_MemberIdAndBooks_BookId(
                members.getMemberId(),books.getBookId());

        assertEquals(list.size(),transactions1.size());
        assertEquals(list,transactions1);
    }
}