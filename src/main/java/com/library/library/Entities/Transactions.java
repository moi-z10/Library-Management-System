package com.library.library.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transactionId;
    private String transactionType;
    private Date dueDate;
    private Date returnDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bookId")
    private Books books;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "memberId")
    private Members members;
}
