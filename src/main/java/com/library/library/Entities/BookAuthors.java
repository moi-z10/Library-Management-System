package com.library.library.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class BookAuthors {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bookId")
    @JsonBackReference

    private Books books;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "authorId")
    @JsonBackReference
    private Authors authors;

}
