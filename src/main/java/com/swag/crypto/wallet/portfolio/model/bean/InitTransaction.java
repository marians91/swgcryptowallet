package com.swag.crypto.wallet.portfolio.model.bean;

import jakarta.validation.constraints.NotNull;


public class InitTransaction {
    @NotNull
    private String receiverAddress;
    @NotNull
    private String senderAddress;
    @NotNull
    private String secretWords;
    @NotNull
    private Double amount;

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getSecretWords() {
        return secretWords;
    }

    public void setSecretWords(String secretWords) {
        this.secretWords = secretWords;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
