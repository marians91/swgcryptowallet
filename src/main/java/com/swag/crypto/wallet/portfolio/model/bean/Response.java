package com.swag.crypto.wallet.portfolio.model.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response <T> {
    private String outcome;
    private String message;
    private T payload;
}
