package com.swag.crypto.wallet.portfolio.controller;

import com.swag.crypto.wallet.portfolio.connector.ExternalConnector;
import com.swag.crypto.wallet.portfolio.exception.AccountNotFoundException;
import com.swag.crypto.wallet.portfolio.exception.NotAuthorizedException;
import com.swag.crypto.wallet.portfolio.model.bean.Crypto;
import com.swag.crypto.wallet.portfolio.model.bean.InitTransaction;
import com.swag.crypto.wallet.portfolio.model.dto.TransactionDTO;
import com.swag.crypto.wallet.portfolio.model.dto.WalletDTO;
import com.swag.crypto.wallet.portfolio.entity.Wallet;
import com.swag.crypto.wallet.portfolio.repository.mapper.WalletMapper;
import com.swag.crypto.wallet.portfolio.service.BankingService;
import com.swag.crypto.wallet.portfolio.service.TransactionService;
import com.swag.crypto.wallet.portfolio.service.WalletService;
import com.swag.crypto.wallet.portfolio.wallet.generator.KeyGenerator;
import com.swag.crypto.wallet.user.service.UserService;
import jakarta.validation.Valid;
import org.bitcoinj.crypto.ECKey;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.swag.crypto.wallet.core.constants.Constant.Path.TRANSACTION;
import static com.swag.crypto.wallet.user.Utils.UserUtils.extractMnemonichPhrase;

@RequestMapping(TRANSACTION)
@Controller
public class TransactionController {

    private final TransactionService transactionService;
    private final WalletService walletService;

    private final BankingService bankingService;

    private final UserService userService;

    public TransactionController(TransactionService transactionService, WalletService walletService, BankingService bankingService, UserService userService) {
        this.transactionService = transactionService;
        this.walletService = walletService;
        this.bankingService = bankingService;
        this.userService = userService;
    }

    @PostMapping("/send/{address}")
    public ModelAndView send(@Valid @ModelAttribute("transaction") InitTransaction transaction, BindingResult result,
                             @PathVariable("address") String address, Model model) {
        ModelAndView mav = new ModelAndView("portfolio");


        checkAccount(address);
        checkAccount(transaction.getReceiverAddress());
        checkPermission(address, extractMnemonichPhrase(transaction.getSecretWords()));

        TransactionDTO transactionDto = transactionService.send(address, transaction.getReceiverAddress(),
                transaction.getAmount(), extractMnemonichPhrase(transaction.getSecretWords()));

        Wallet wallet = walletService.findByAddress(address).get();
        WalletDTO walletDto = WalletMapper.INSTANCE.walletToWallweDto(wallet);

        mav.addObject("wallet", walletDto);
        return mav;
    }

    @GetMapping("/redirect/send/{address}")
    public String redirectSend(@PathVariable("address") String address,
                               Model model) {

        Wallet wallet = walletService.findByAddress(address).get();
        WalletDTO walletDto = WalletMapper.INSTANCE.walletToWallweDto(wallet);
        InitTransaction transaction = new InitTransaction();
        transaction.setSenderAddress(address);
        model.addAttribute("transaction", transaction);
        return "authorize";
    }

    @GetMapping("/redirect/portfolio/{address}")
    public ModelAndView redirectPortfolio(@PathVariable("address") String address,
                                          Model model) {
        ModelAndView mav = new ModelAndView("portfolio");
        Wallet wallet = walletService.findByAddress(address).get();
        WalletDTO walletDto = WalletMapper.INSTANCE.walletToWallweDto(wallet);
        Crypto crypto = ExternalConnector.btcRTdata();
        walletDto.setCrypto(crypto);
        mav.addObject("wallet", walletDto);

        return mav;
    }


    public void checkAccount(String address) {

        Optional<Wallet> wallet = walletService.findByAddress(address);
        if (!wallet.isPresent()) {
            throw new AccountNotFoundException(address);
        }

    }

    private void checkPermission(String address, List<String> words) {

        ECKey keys = KeyGenerator.keyPair(words);
        String privateKey = KeyGenerator.privateKey(keys);
        Optional<Wallet> wallet = walletService.findByAddress(address);

        boolean authorized = false;
        if (!wallet.isPresent()) {
            throw new AccountNotFoundException();
        } else {
            authorized = checkKeyMatch(privateKey, wallet.get());
        }
        if (authorized != true) throw new NotAuthorizedException(address);

    }

    private boolean checkKeyMatch(String privateKey, Wallet wallet) {
        if (wallet.getPrivateKey().equals(privateKey))
            return true;
        else
            return false;
    }
}
