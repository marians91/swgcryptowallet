package com.swag.crypto.wallet.user.controller;

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

import static com.swag.crypto.wallet.core.constants.Constant.Path.USER;
import static com.swag.crypto.wallet.user.Utils.UserUtils.mapToUserDto;

@RequestMapping(USER)
@Controller
public class UserController {
    private final UserServiceImpl userService;
    private final WalletService walletService;

    public UserController(UserServiceImpl userService, WalletService walletService) {
        this.userService = userService;
        this.walletService = walletService;
    }


    @GetMapping("/{userId}")
    public ModelAndView users(@PathVariable("userId") Long userId) {
        ModelAndView mv = new ModelAndView("user");
        User user = userService.findById(userId);
        UserDTO userDto = mapToUserDto(user);
        mv.addObject("user", userDto);
        return mv;
    }

    @PostMapping("/update/{id}")
    public ModelAndView update(@Valid @ModelAttribute("user") UserDTO userDto,
                               BindingResult result,
                               @PathVariable("id") Long id) {

        ModelAndView mav = new ModelAndView("update");
        userService.update(userDto, id);
        mav.addObject("user", userDto);
        mav.addObject("outcome", "success");
        return mav;
    }

    @GetMapping("/update/{id}")
    public String redirectUpdate(@PathVariable Long id,
                                 Model model) {
        User user = userService.findById(id);
        UserDTO userDto = mapToUserDto(user);
        model.addAttribute("user", userDto);
        return "/update";
    }

    @GetMapping("/delete/{email}")
    public ModelAndView delete(@PathVariable String email,
                               Model model) {
        ModelAndView mav = new ModelAndView("register");
        UserDTO userDto = new UserDTO();
        userService.delete(email);
        mav.addObject("outcome", "deleted");
        mav.addObject("user",userDto);
        return mav;
    }


}
