package com.library.library.Repository;


import com.library.library.Entities.Books;
import com.library.library.Entities.Members;
import com.library.library.Entities.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionsRepo extends JpaRepository<Transactions,Long> {


    Optional<Transactions> findByMembersAndBooks(Members members,Books books);
    List<Transactions> findByBooks(Books book);
    List<Transactions> findByMembers(Members members);
    List<Transactions> findListByMembersAndBooks(Members members,Books books);
}
