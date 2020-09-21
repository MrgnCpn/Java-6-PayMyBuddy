package com.paymybuddy.paymybuddyweb.controllers;

import com.paymybuddy.paymybuddyweb.interfaces.Utils.ControllerUtilsInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.CountryServiceInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.SecurityServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Singleton;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MorganCpn
 */
@RestController
@Singleton
public class AuthenticateController {
    @Autowired
    private SecurityServiceInterface securityService;

    @Autowired
    private CountryServiceInterface countryService;

    @Autowired
    private ControllerUtilsInterface controllerUtils;


    @GetMapping("/login")
    public ModelAndView getLogin(HttpSession session){
        if (securityService.isLog(session)) return controllerUtils.rootRedirect();

        Map<String, Object> model = new HashMap<>();
        model.put("page", "login");
        model.put("isLogin", securityService.isLog(session));

        return new ModelAndView("template.html" , model);
    }

    @PostMapping("/login")
    public ModelAndView postLogin(HttpSession session, @RequestParam(required = true) Map<String,String> requestParams){
        Map<String, String> userLogin = securityService.logUser(requestParams.get("username"), requestParams.get("password"), requestParams.get("remember") != null);

        if (userLogin != null) {
            userLogin.forEach((k, v) -> session.setAttribute(k, v));
        }

        return controllerUtils.rootRedirect();
    }

    @GetMapping("/signup")
    public ModelAndView getSignup(HttpSession session) throws IOException {
        if (securityService.isLog(session)) return controllerUtils.rootRedirect();

        Map<String, Object> model = new HashMap<>();
        model.put("page", "signup");
        model.put("isLogin", securityService.isLog(session));
        model.put("countries", countryService.getAllCountries());

        return new ModelAndView("template.html" , model);
    }

    @PostMapping("/signup")
    public ModelAndView postSignup(HttpSession session, @RequestParam(required = true) Map<String, Object> requestParams) throws IOException {
        Map<String, String> userLogin = securityService.registerUser(requestParams);

        if (userLogin != null) {
            userLogin.forEach((k, v) -> session.setAttribute(k, v));
        }

        return controllerUtils.rootRedirect();
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session){
        session.invalidate();
        return controllerUtils.rootRedirect();
    }

    @PostMapping("/change-password")
    public ModelAndView changePassword(HttpSession session, @RequestParam(required = true) Map<String, Object> requestParams){
        securityService.updateUserPassword(session, requestParams);
        return controllerUtils.doRedirect("/profile");
    }
}