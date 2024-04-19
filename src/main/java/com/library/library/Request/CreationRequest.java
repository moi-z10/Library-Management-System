package com.library.library.Request;

import com.library.library.Entities.Authors;
import com.library.library.Entities.Genre;
import com.library.library.Entities.Publisher;
import lombok.Data;

import java.util.UUID;
@Data
public class CreationRequest {

        private String bookId;
        private Genre genre;
        private Publisher publisher;
        private Authors authors;
        private String title;
        private String isbn;
        private int publicationYear;
        private int quantity;
}

