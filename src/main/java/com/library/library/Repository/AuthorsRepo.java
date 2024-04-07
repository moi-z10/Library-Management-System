package com.library.library.Repository;

import com.library.library.Entities.Authors;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorsRepo extends JpaRepository<Authors,Long> {
    Optional<Authors> findByauthorName(String authorName);
}
