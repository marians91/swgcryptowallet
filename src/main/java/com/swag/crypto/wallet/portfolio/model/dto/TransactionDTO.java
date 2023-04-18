package com.swag.crypto.wallet.portfolio.model.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TransactionDTO {
    private Long id;
    private String message;
    private String outcome;
    private BigDecimal amount;
    private String sender;
    private String receiver;
    private Timestamp executionTime;
}
