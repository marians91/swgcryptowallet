package com.swag.crypto.wallet.portfolio.service.impl;

import com.swag.crypto.wallet.portfolio.entity.Wallet;
import com.swag.crypto.wallet.portfolio.exception.AccountNotFoundException;
import com.swag.crypto.wallet.portfolio.exception.NotAuthorizedException;
import com.swag.crypto.wallet.portfolio.exception.OperationNotSupportedException;
import com.swag.crypto.wallet.portfolio.repository.WalletRepository;
import com.swag.crypto.wallet.portfolio.service.BankingService;
import com.swag.crypto.wallet.portfolio.wallet.generator.KeyGenerator;
import jakarta.transaction.Transactional;
import org.bitcoinj.crypto.ECKey;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankingServiceImpl implements BankingService {
    private final WalletRepository walletRepository;

    public BankingServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    @Transactional
    public void add(Wallet senderWallet, Wallet receiverWallet, Double amountToTransfer) {

        Double receiverAmount = receiverWallet.getBtcAmount();
        Double updatedAmount = receiverAmount + amountToTransfer;
        walletRepository.updateWalletAmount(updatedAmount, receiverWallet.getAddress());
        Wallet wallet = walletRepository.findByAddress(receiverWallet.getAddress()).get();

    }

    @Override
    @Transactional
    public void remove(Wallet sender, Wallet receiver, Double amountToRemove) {

        Double senderAmount = sender.getBtcAmount();
        if (senderAmount < amountToRemove) throw new OperationNotSupportedException();
        Double updatedAmount = senderAmount - amountToRemove;

        walletRepository.updateWalletAmount(updatedAmount, sender.getAddress());
        Wallet wallet = walletRepository.findByAddress(sender.getAddress()).get();

    }

    @Transactional
    @Override
    public void send(String sender, String receiver, Double amount, List<String> mnemonicSeedPhrase) {
        Optional<Wallet> senderWallet = walletRepository.findByAddress(sender);
        if (!senderWallet.isPresent()) throw new AccountNotFoundException(sender);

        Optional<Wallet> receiverWallet = walletRepository.findByAddress(receiver);
        if (!receiverWallet.isPresent()) throw new AccountNotFoundException(receiver);

        if (!authorize(sender, mnemonicSeedPhrase)) throw new NotAuthorizedException(sender);

        remove(senderWallet.get(), receiverWallet.get(), amount);
        add(senderWallet.get(), receiverWallet.get(), amount);
    }

    @Override
    public boolean authorize(String address, List<String> mnemonicSeedPhrase) {
        ECKey keys = KeyGenerator.keyPair(mnemonicSeedPhrase);
        String privateKey = KeyGenerator.privateKey(keys);
        Optional<Wallet> wallet = walletRepository.findByAddress(address);

        boolean authorized = false;
        if (!wallet.isPresent()) {
            throw new AccountNotFoundException();
        } else {
            authorized = checkPermission(privateKey, wallet.get());
            return authorized;
        }
    }

    private boolean checkPermission(String privateKey, Wallet wallet) {
        if (wallet.getPrivateKey().equals(privateKey))
            return true;
        else
            return false;
    }
}
