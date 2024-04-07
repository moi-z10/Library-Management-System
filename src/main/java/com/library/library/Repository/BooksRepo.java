package com.library.library.Repository;

import com.library.library.Entities.Books;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepo extends JpaRepository<Books,Long> {
    Books findBytitle(String title);
}
