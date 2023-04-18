package com.swag.crypto.wallet.user.controller;

import com.swag.crypto.wallet.portfolio.connector.ExternalConnector;
import com.swag.crypto.wallet.portfolio.model.bean.Crypto;
import com.swag.crypto.wallet.portfolio.model.bean.InitWallet;
import com.swag.crypto.wallet.portfolio.model.dto.WalletDTO;
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
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

import static com.swag.crypto.wallet.user.Utils.UserUtils.mapToUserDto;

@Controller
public class AuthController {

    private final UserServiceImpl userService;
    private final WalletService walletService;

    public AuthController(UserServiceImpl userService, WalletService walletService) {
        this.userService = userService;
        this.walletService = walletService;
    }


    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDTO userDto,
                               BindingResult result,
                               Model model) {
        User existingUser = userService.findByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.save(userDto);
        return "redirect:/register?success";
    }


    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        return "register";
    }

    @GetMapping("/users")
    public String users(Model model, Principal principal) {
        String loggedUser = principal.getName();
        List<UserDTO> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }



    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/portfolio/{userId}")
    public ModelAndView portfolio(@PathVariable("userId") String userId) {

        ModelAndView mv = new ModelAndView();
        User user = userService.findById(Long.valueOf(userId));
        boolean hasAccount = user.getWallet() != null ? true : false;
        UserDTO userDto = mapToUserDto(user);
        Crypto crypto = ExternalConnector.btcRTdata();
        if (hasAccount) {
            WalletDTO walletDto = WalletMapper.INSTANCE.walletToWallweDto(walletService.findByUserId(user.getId()).get());
            walletDto.setCrypto(crypto);
            mv.addObject("wallet", walletDto);
            mv.setViewName("portfolio");
            return mv;
        } else {
            InitWallet initWallet = new InitWallet();
            initWallet.setUser(userDto);
            mv.addObject("user", userDto);
            mv.setViewName("landing");
        }
        return mv;
    }


}
