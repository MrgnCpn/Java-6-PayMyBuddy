package com.paymybuddy.PayMyBuddyWeb.controllers;

import com.paymybuddy.PayMyBuddyWeb.interfaces.Utils.ControllerUtilsInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.CreditCardServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.SecurityServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.TransactionServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.UserServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Singleton;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@Singleton
public class AccountController {
    @Autowired
    private SecurityServiceInterface securityService;

    @Autowired
    private UserServiceInterface userService;

    @Autowired
    private CreditCardServiceInterface creditCardService;

    @Autowired
    private ControllerUtilsInterface controllerUtils;

    @Autowired
    private TransactionServiceInterface transactionService;

    @GetMapping("/account")
    public ModelAndView getAccount(HttpSession session) {
        if (!securityService.isLog(session)) return controllerUtils.rootRedirect();

        User user = userService.getUser(session);
        Map<String, Object> model = new HashMap<>();
        model.put("page", "account");
        model.put("isLogin", securityService.isLog(session));
        model.put("user", user);
        model.put("transactions", transactionService.getUserTransaction(session));
        return new ModelAndView("template.html" , model);
    }

    @GetMapping("/manage-card")
    public ModelAndView getCardsList(HttpSession session) {
        if (!securityService.isLog(session)) return controllerUtils.rootRedirect();

        User user = userService.getUser(session);
        Map<String, Object> model = new HashMap<>();
        model.put("page", "manage-card");
        model.put("isLogin", securityService.isLog(session));
        model.put("cards", user.getCreditCards());
        return new ModelAndView("template.html" , model);
    }

    @GetMapping("/manage-card/card/edit/{id}")
    public ModelAndView getCard(HttpSession session, @PathVariable(value="id") Integer cardId){
        if (!securityService.isLog(session)) return controllerUtils.rootRedirect();

        Map<String, Object> model = new HashMap<>();
        model.put("page", "card");
        model.put("isLogin", securityService.isLog(session));
        model.put("card", creditCardService.getUserCardById(session, cardId));
        return new ModelAndView("template.html" , model);
    }

    @GetMapping("/manage-card/card/remove/{id}")
    public ModelAndView getRemoveCard(HttpSession session, @PathVariable(value="id") Integer cardId){
        if (!securityService.isLog(session)) return controllerUtils.rootRedirect();
        creditCardService.removeCard(session, cardId);
        return controllerUtils.doRedirect("/manage-card");
    }

    @GetMapping("/manage-card/card/new")
    public ModelAndView getNewCard(HttpSession session){
        if (!securityService.isLog(session)) return controllerUtils.rootRedirect();

        Map<String, Object> model = new HashMap<>();
        model.put("page", "card");
        model.put("isLogin", securityService.isLog(session));
        model.put("card", null);
        return new ModelAndView("template.html" , model);
    }

}
