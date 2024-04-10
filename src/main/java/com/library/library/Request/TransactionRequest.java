package com.library.library.Request;

import lombok.Data;

import java.util.UUID;

@Data
public class TransactionRequest {
    private String bookId;
    private String memberId;
    private String transactionType;
}
