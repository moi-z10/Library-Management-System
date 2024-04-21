package com.library.library.Repository;

import com.library.library.Entities.Authors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AuthorsRepoTest {



    @Test
    void findByauthorName() {
        AuthorsRepo authorsRepo = Mockito.mock(AuthorsRepo.class);
        Authors authors = new Authors();
        authors.setAuthorId("12");
        authors.setAuthorName(("omer"));

        when(authorsRepo.findByauthorName(authors.getAuthorName())).
                thenReturn(Optional.of(authors));
        Optional<Authors> authors1 = authorsRepo.findByauthorName(authors.getAuthorName());

        assertEquals(authors,authors1.get());
    }

}