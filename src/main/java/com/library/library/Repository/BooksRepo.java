package com.library.library.Repository;

import com.library.library.Entities.Books;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BooksRepo extends JpaRepository<Books, String> {
    Optional<Books> findByTitle(String title);
}
