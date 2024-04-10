package com.library.library.DTO;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class TransactionsDTO {
    private String bookId;
    private String memberId;
    private String transactionId;
    private String transactionType;
    private Date dueDate;
    private Date returnDate;

}
