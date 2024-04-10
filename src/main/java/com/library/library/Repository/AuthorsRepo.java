package com.library.library.Repository;

import com.library.library.Entities.Authors;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorsRepo extends JpaRepository<Authors, String> {
    Optional<Authors> findByauthorName(String authorName);
}
