package com.library.library.Repository;

import com.library.library.Entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GenreRepo extends JpaRepository<Genre, String> {
    Optional<Genre> findBygenreName(String genreName);
}
