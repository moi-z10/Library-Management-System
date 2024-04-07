package com.library.library.Request;

import lombok.Data;

@Data
public class TransactionRequest {
    private long bookId;
    private long memberId;
    private String transactionType;
}
