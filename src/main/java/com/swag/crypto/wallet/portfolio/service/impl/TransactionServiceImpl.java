package com.swag.crypto.wallet.portfolio.service.impl;

import com.swag.crypto.wallet.portfolio.model.dto.TransactionDTO;
import com.swag.crypto.wallet.portfolio.entity.Transaction;
import com.swag.crypto.wallet.portfolio.repository.TransactionRepository;
import com.swag.crypto.wallet.portfolio.repository.WalletRepository;
import com.swag.crypto.wallet.portfolio.repository.mapper.TransactionMapper;
import com.swag.crypto.wallet.portfolio.service.BankingService;
import com.swag.crypto.wallet.portfolio.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {
    private WalletRepository walletRepository;
    private BankingService bankingService;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(WalletRepository walletRepository, BankingService bankingService, TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.bankingService = bankingService;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public TransactionDTO send(String sender, String receiver, Double amount, List<String> mnemonicSeedPhrase) {
        Transaction transaction = Transaction.builder().sender(sender).receiver(receiver).amount(amount).build();
        TransactionMapper MAPPER = TransactionMapper.INSTANCE;
        try {
            bankingService.send(sender, receiver, amount, mnemonicSeedPhrase);
        } catch (Exception ex) {
            transaction.setOutcome("KO");
            transaction.setMessage(ex.getLocalizedMessage());
            transactionRepository.save(transaction);
            return MAPPER.transactionToTransactionDto(transaction);
        }
        transaction.setOutcome("OK");
        transaction.setMessage("OK");
        transactionRepository.save(transaction);
        return MAPPER.transactionToTransactionDto(transaction);
    }

    @Override
    public String receive(String address) {
        return address;
    }

}
