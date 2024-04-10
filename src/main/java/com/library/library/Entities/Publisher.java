package com.library.library.Entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.aop.target.LazyInitTargetSource;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Publisher {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String publisherId;
    private String publisherName;

//    @OneToMany(mappedBy = "publisher")
//    private List<Books> books;
}
