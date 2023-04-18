package com.swag.crypto.wallet.portfolio.controller;

import com.swag.crypto.wallet.portfolio.connector.ExternalConnector;
import com.swag.crypto.wallet.portfolio.model.bean.Crypto;
import com.swag.crypto.wallet.portfolio.model.dto.WalletDTO;
import com.swag.crypto.wallet.portfolio.model.bean.InitWallet;
import com.swag.crypto.wallet.portfolio.repository.mapper.WalletMapper;
import com.swag.crypto.wallet.portfolio.service.WalletService;
import com.swag.crypto.wallet.user.dto.UserDTO;
import com.swag.crypto.wallet.user.entity.User;
import com.swag.crypto.wallet.user.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;

import static com.swag.crypto.wallet.core.constants.Constant.Path.WALLET;
import static com.swag.crypto.wallet.user.Utils.UserUtils.mapToUserDto;


@Controller
@RequestMapping(WALLET)
public class WalletController {
    private final WalletService walletService;
    private final UserServiceImpl userService;

    public WalletController(WalletService walletService, UserServiceImpl userService) {
        this.walletService = walletService;
        this.userService = userService;
    }

    @PostMapping("/create/{userId}")
    public String create(@Valid @ModelAttribute("wallet") InitWallet initWallet, @PathVariable String userId, BindingResult result,
                         Model model) {

        User user = userService.findById(Long.valueOf(userId));
        WalletDTO wallet = walletService.create(Arrays.asList(initWallet.getMnemonicSeedPhrase().split(" ")), mapToUserDto(user));
        Crypto currency = ExternalConnector.btcRTdata();
        wallet.setCrypto(currency)
        ;
        model.addAttribute("wallet", wallet);
        if (result.hasErrors()) {
            result.rejectValue("Error", "00");
        }
        return "portfolio";
    }

    @PostMapping("/import/{userId}")
    public String importWallet(@Valid @ModelAttribute("wallet") InitWallet initWallet, @PathVariable String userId, BindingResult result,
                               Model model) {
        User user = userService.findById(Long.valueOf(userId));
        Crypto crypto = ExternalConnector.btcRTdata();
        WalletDTO wallet = walletService.importWallet(Arrays.asList(initWallet.getMnemonicSeedPhrase().split(" ")), mapToUserDto(user));
        wallet.setCrypto(crypto);

        if (result.hasErrors()) {
            result.rejectValue("Error", "00");
        }

        model.addAttribute("wallet", wallet);
        return "portfolio";
    }


    @GetMapping("/redirect/create/{userId}")
    public String redirectCreate(@PathVariable Long userId,
                                 Model model) {
        UserDTO userDto = mapToUserDto(userService.findById(userId));
        InitWallet wallet = new InitWallet();
        wallet.setUser(userDto);
        wallet.setTypeOfOperation("create");
        model.addAttribute("wallet", wallet);
        return "seedsPhrase";
    }

    @GetMapping("/redirect/import/{userId}")
    public String redirectImport(@PathVariable Long userId,
                                 Model model) {
        UserDTO userDto = mapToUserDto(userService.findById(userId));
        InitWallet wallet = new InitWallet();
        wallet.setUser(userDto);
        wallet.setTypeOfOperation("import");
        model.addAttribute("wallet", wallet);
        return "seedsPhrase";
    }

    @GetMapping("/init")
    public String initialize(Model model, Principal principal) {
        String loggedUser = principal.getName();
        User user = userService.findByEmail(loggedUser);
        boolean hasAccount = user.getWallet() != null ? true : false;
        UserDTO userDto = mapToUserDto(user);
        Crypto crypto = ExternalConnector.btcRTdata();
        if (hasAccount) {
            WalletDTO walletDto = WalletMapper.INSTANCE.walletToWallweDto(walletService.findByUserId(user.getId()).get());
            walletDto.setCrypto(crypto);
            model.addAttribute("wallet", walletDto);
            return "portfolio";
        } else {
            InitWallet initWallet = new InitWallet();
            initWallet.setUser(userDto);
            model.addAttribute("user", userDto);
        }
        return "landing";
    }

}
