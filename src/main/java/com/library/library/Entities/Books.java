package com.library.library.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class Books {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String bookId;
    private String title;
    private String isbn;
    private int publishingYear;
    private int quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "genreId")
    @JsonIgnore

    private Genre genre;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "publisherId")
    @JsonIgnore
    private Publisher publisher;


    @ManyToMany
    @JoinTable(name = "BookAuthors",
            joinColumns = @JoinColumn(name = "BookId"),
            inverseJoinColumns = @JoinColumn(name = "AuthorId")
    )
    @JsonIgnore
    private Set<Authors> authorsSet = new HashSet<>();


    @OneToMany(mappedBy = "books")
    @JsonIgnore
    private List<Transactions> transactions;

}
