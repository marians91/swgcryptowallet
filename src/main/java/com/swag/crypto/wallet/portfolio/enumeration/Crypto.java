package com.swag.crypto.wallet.portfolio.enumeration;

public enum Crypto {
    BTC("Bitcoin","BTC"),
    ETH("Ethereum","ETH");
    Crypto(String name, String code) {
        this.name = name;
        this.code = code;
    }

    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
