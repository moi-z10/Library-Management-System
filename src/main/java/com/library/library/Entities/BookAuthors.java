package com.library.library.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore

    private Books books;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "authorId")
    @JsonIgnore
    private Authors authors;

}
