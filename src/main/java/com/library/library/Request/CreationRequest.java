package com.library.library.Request;

import lombok.Data;

public class CreationRequest {

    @Data
    public static class CreationDto {
        private long bookId;
        private long genreId;
        private long publisherId;
        private String genreName;
        private String publisherName;
        private String authorName;
        private long authorId;
        private String title;
        private String isbn;
        private int publicationYear;
        private int quantity;

    }
}
