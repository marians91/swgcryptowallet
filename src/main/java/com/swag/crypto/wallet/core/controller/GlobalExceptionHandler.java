package com.swag.crypto.wallet.core.controller;

import com.swag.crypto.wallet.portfolio.exception.AccountNotFoundException;
import com.swag.crypto.wallet.portfolio.exception.NotAuthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import static com.swag.crypto.wallet.core.constants.Constant.*;
import static com.swag.crypto.wallet.core.constants.Constant.ErrorMessage.IMPORT_PHASE;
import static com.swag.crypto.wallet.core.constants.Constant.ErrorMessage.TRANSFER_FUNDS_PHASE;

import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler implements ErrorController {
 
    private static final String PATH = "/error";



    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleException(RuntimeException ex) {
        ex.printStackTrace();
        ModelAndView model = new ModelAndView();
        model.addObject("errCode", ErrorCode.SERVER_ERROR);
        model.addObject("errMsg", ErrorMessage.UNEXPECTED_ERROR);
        model.setViewName("error");
        return model;
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ModelAndView handleAccountNotFoundException(AccountNotFoundException ex) {
        ex.printStackTrace();
        ModelAndView model = new ModelAndView();
        if(ex.getCode().equals(IMPORT_PHASE)){
            model.addObject("userId", ex.getUserId());
            model.setViewName("importAccountNotFound");
        }
        if(ex.getCode().equals(TRANSFER_FUNDS_PHASE)){
            model.addObject("account", ex.getAccount());
            model.setViewName("accountNotFound");
        }
        return model;
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ModelAndView handleException(NotAuthorizedException ex) {
        ex.printStackTrace();
        ModelAndView model = new ModelAndView();

        model.addObject("account", ex.getMessage());
        model.setViewName("userNotAuth");
        return model;
    }

    public String getErrorPath() {
        return PATH;
    }
}
