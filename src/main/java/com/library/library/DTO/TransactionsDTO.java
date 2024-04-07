package com.library.library.DTO;

import lombok.Data;

import java.util.Date;
@Data
public class TransactionsDTO {
    long bookId;
    long memberId;
    private long transactionId;
    private String transactionType;
    private Date dueDate;
    private Date returnDate;
}
