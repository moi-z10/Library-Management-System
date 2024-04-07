package com.library.library.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Genre {
    @Id
    private long genreId;
    private String genreName;

    @OneToMany(mappedBy = "genre")
    @JsonBackReference
    private List<Books> books;
}
