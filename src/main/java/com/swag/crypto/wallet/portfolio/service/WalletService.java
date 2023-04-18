package com.swag.crypto.wallet.portfolio.service;

import com.swag.crypto.wallet.portfolio.model.dto.WalletDTO;
import com.swag.crypto.wallet.portfolio.entity.Wallet;
import com.swag.crypto.wallet.user.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface WalletService {

    public WalletDTO create(List<String> mnemonicSeedPhrase, UserDTO userDto);

    public Optional<Wallet> findByUserId(Long id);

    public Optional<Wallet> findByAddress(String address);

    public WalletDTO importWallet(List<String> mnemonicSeedPhrase, UserDTO userDto);

    public Wallet encrypt(Wallet wallet, List<String> mnemonicSeedPhrase);

    void assignOwner(Wallet wallet, UserDTO userDto);

    boolean checkPermission(String privateKey, Wallet wallet);
}
