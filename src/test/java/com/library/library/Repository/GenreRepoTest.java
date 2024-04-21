package com.library.library.Repository;

import com.library.library.Entities.Genre;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GenreRepoTest {

    @Test
    void findBygenreName() {
        GenreRepo genreRepo = Mockito.mock(GenreRepo.class);

        Genre genre = new Genre();
        genre.setGenreName("Hello");
        genre.setGenreId("100");

        when(genreRepo.findBygenreName(genre.getGenreName())).thenReturn(Optional.of(genre));
        Optional<Genre> optionalGenre = genreRepo.findBygenreName(genre.getGenreName());
        assertEquals(genre,optionalGenre.get());
    }
}