package com.paymybuddy.PayMyBuddyWeb.controllers;

import com.paymybuddy.PayMyBuddyWeb.interfaces.Utils.ControllerUtilsInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.*;
import com.paymybuddy.PayMyBuddyWeb.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Singleton;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
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

    @Autowired
    private AccountServiceInterface accountService;

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

    @GetMapping("/feed-account")
    public ModelAndView getFeedAccount(HttpSession session) {
        if (!securityService.isLog(session)) return controllerUtils.rootRedirect();

        User user = userService.getUser(session);
        Map<String, Object> model = new HashMap<>();
        model.put("page", "feed-account");
        model.put("isLogin", securityService.isLog(session));
        model.put("user", user);
        model.put("cards", user.getCreditCards());
        return new ModelAndView("template.html" , model);
    }

    @PostMapping("/feed-account")
    public ModelAndView postFeedAccount(HttpSession session, @RequestParam(required = true) Map<String, Object> requestParams) throws SQLException {
        User user = userService.getUser(session);
        accountService.feedAccount(session, requestParams, user);
        return controllerUtils.doRedirect("/account");
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

    @PostMapping("/manage-card/card/edit/{id}")
    public ModelAndView postCard(HttpSession session, @PathVariable(value="id") Integer cardId, @RequestParam(required = true) Map<String, Object> requestParams){
        creditCardService.updateCard(session, cardId, requestParams);
        return controllerUtils.doRedirect("/manage-card/card/edit/" + cardId);
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

    @PostMapping("/manage-card/card/new")
    public ModelAndView postNewCard(HttpSession session, @RequestParam(required = true) Map<String, Object> requestParams){
        creditCardService.addNewCard(session, requestParams);
        return controllerUtils.doRedirect("/manage-card");
    }
}
