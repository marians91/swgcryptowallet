package com.swag.crypto.wallet.portfolio.service;

import com.swag.crypto.wallet.portfolio.entity.Wallet;

import java.util.List;

public interface BankingService {

    void add(Wallet sender, Wallet receiver, Double amount);

    void remove(Wallet sender, Wallet receiver, Double amount);

    void send(String sender, String receiver, Double amount, List<String> mnemonicSeedPhrase);

    boolean authorize(String address, List<String> mnemonicSeedPhrase);
}
