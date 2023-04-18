package com.swag.crypto.wallet.portfolio.model.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Crypto {

    private String currency;
    private String base;
    private double amount;

}
