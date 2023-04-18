package com.swag.crypto.wallet.portfolio.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperationNotSupportedException extends RuntimeException {
    private String message;
    private int code;
}
