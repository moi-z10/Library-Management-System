package com.library.library.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Books {
    @Id
    private long bookId;
    private String title;
    private String author;
    private String isbn;
    private int publishingYear;
    private int quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "genreId")
    @JsonBackReference

    private Genre genre;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "publisherId")
    @JsonBackReference

    private Publisher publisher;

    @OneToOne(mappedBy = "books")
    @JsonBackReference
    private BookAuthors bookAuthors;

    @OneToMany(mappedBy = "books")
    @JsonBackReference

    private List<Transactions> transactions;

}
