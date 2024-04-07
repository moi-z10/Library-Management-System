package com.library.library.Repository;

import com.library.library.Entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepo extends JpaRepository<Genre,Long> {
    Optional<Genre> findBygenreName(String genreName);
}
