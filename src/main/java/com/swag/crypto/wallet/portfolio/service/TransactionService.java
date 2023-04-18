package com.swag.crypto.wallet.portfolio.service;

import com.swag.crypto.wallet.portfolio.model.dto.TransactionDTO;


import java.util.List;

public interface TransactionService {

    TransactionDTO send(String sender, String receiver, Double amount, List<String> mnemonicSeedPhrase);

    String receive(String address);

}
