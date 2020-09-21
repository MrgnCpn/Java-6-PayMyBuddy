package com.paymybuddy.paymybuddyweb.controllers;

import com.paymybuddy.paymybuddyweb.interfaces.Utils.ControllerUtilsInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.SecurityServiceInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.TransactionServiceInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.UserServiceInterface;
import com.paymybuddy.paymybuddyweb.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Singleton;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MorganCpn
 */
@RestController
@Singleton
public class TransferController {

    @Autowired
    private ControllerUtilsInterface controllerUtils;

    @Autowired
    private SecurityServiceInterface securityService;

    @Autowired
    private UserServiceInterface userService;

    @Autowired
    private TransactionServiceInterface transactionService;

    @GetMapping("/transfer")
    public ModelAndView getTransfer(HttpSession session) {
        if (!securityService.isLog(session)) return controllerUtils.rootRedirect();

        Map<String, Object> model = new HashMap<>();
        User user = userService.getUser(session);
        model.put("page", "transfer");
        model.put("isLogin", securityService.isLog(session));
        model.put("friends", userService.getUserFriends(user));

        return new ModelAndView("template.html" , model);
    }

    @PostMapping("/transfer")
    public ModelAndView postCard(HttpSession session, @RequestParam(required = true) Map<String, Object> requestParams) throws IOException, SQLException {
        transactionService.doTransfer(session, requestParams);
        return controllerUtils.doRedirect("/account");
    }
}
