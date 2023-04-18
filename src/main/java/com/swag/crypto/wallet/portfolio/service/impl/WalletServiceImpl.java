package com.swag.crypto.wallet.portfolio.service.impl;

import com.swag.crypto.wallet.portfolio.exception.AccountNotFoundException;
import com.swag.crypto.wallet.portfolio.model.dto.WalletDTO;
import com.swag.crypto.wallet.portfolio.entity.Wallet;
import com.swag.crypto.wallet.portfolio.repository.WalletRepository;
import com.swag.crypto.wallet.portfolio.repository.mapper.WalletMapper;
import com.swag.crypto.wallet.portfolio.service.WalletService;
import com.swag.crypto.wallet.portfolio.wallet.generator.BitcoinWalletGenerator;
import com.swag.crypto.wallet.portfolio.wallet.generator.KeyGenerator;
import com.swag.crypto.wallet.user.dto.UserDTO;
import com.swag.crypto.wallet.user.entity.User;
import com.swag.crypto.wallet.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.bitcoinj.crypto.ECKey;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.swag.crypto.wallet.core.constants.Constant.ErrorMessage.IMPORT_PHASE;

@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    public WalletServiceImpl(WalletRepository walletRepository, UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public WalletDTO create(List<String> mnemonicSeedPhrase, UserDTO userDto) {

        Wallet wallet = BitcoinWalletGenerator.generateWallet();
        wallet = walletRepository.save(wallet);
        assignOwner(wallet, userDto);
        encrypt(wallet, mnemonicSeedPhrase);
        walletRepository.save(wallet);
        WalletDTO walletDto = WalletMapper.INSTANCE.walletToWallweDto(wallet);
        return walletDto;
    }

    @Override
    public Optional<Wallet> findByUserId(Long id) {
        return walletRepository.findByUserId(id);
    }

    @Override
    public Optional<Wallet> findByAddress(String address) {
        return walletRepository.findByAddress(address);
    }

    @Override
    @Transactional
    public WalletDTO importWallet(List<String> mnemonicSeedPhrase, UserDTO userDto) {
        ECKey keys = KeyGenerator.keyPair(mnemonicSeedPhrase);
        String address = KeyGenerator.address(keys);
        String privateKey = KeyGenerator.privateKey(keys);
        Optional<Wallet> wallet = findByAddress(address);
        boolean authorized = false;
        if (!wallet.isPresent()) {
             throw new AccountNotFoundException(userDto.getId(), "",IMPORT_PHASE);
        } else {
            authorized = checkPermission(privateKey, wallet.get());
            if (authorized == true) {
                removeOwner(wallet.get());
                assignOwner(wallet.get(), userDto);
            }
        }
        WalletDTO walletDto = WalletMapper.INSTANCE.walletToWallweDto(wallet.get());
        return walletDto;
    }


    @Override
    public Wallet encrypt(Wallet wallet, List<String> mnemonicSeedPhrase) {
        ECKey keyPair = KeyGenerator.keyPair(mnemonicSeedPhrase);
        String privateKey = KeyGenerator.privateKey(keyPair);
        String address = KeyGenerator.address(keyPair);
        wallet.setAddress(address);
        wallet.setPrivateKey(privateKey);
        return wallet;
    }

    @Override
    public void assignOwner(Wallet wallet, UserDTO userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        wallet.setUser(user);
        user.setWallet(wallet);
        userRepository.save(user);
    }

    public void removeOwner(Wallet wallet) {
        User user = userRepository.findById(wallet.getUser().getId()).get();
        user.setWallet(null);
        userRepository.save(user);
    }

    @Override
    public boolean checkPermission(String privateKey, Wallet wallet) {
        if (wallet.getPrivateKey().equals(privateKey))
            return true;
        else
            return false;
    }

}
