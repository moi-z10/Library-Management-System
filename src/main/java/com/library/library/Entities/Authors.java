package com.library.library.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Authors {
    @Id
    private long authorId;
    private String authorName;

    @OneToMany(mappedBy = "authors")
    @JsonBackReference
    private List<BookAuthors> bookAuthors;
}
