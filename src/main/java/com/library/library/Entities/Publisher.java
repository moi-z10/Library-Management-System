package com.library.library.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.springframework.aop.target.LazyInitTargetSource;

import java.util.List;

@Entity
@Data
public class Publisher {
    @Id
    private long publisherId;
    private String publisherName;

    @OneToMany(mappedBy = "publisher")
    private List<Books> books;
}
