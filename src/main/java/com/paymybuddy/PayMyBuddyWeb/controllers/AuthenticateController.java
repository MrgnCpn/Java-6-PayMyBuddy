package com.paymybuddy.PayMyBuddyWeb.controllers;

import com.paymybuddy.PayMyBuddyWeb.interfaces.service.SecurityServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Singleton;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Singleton
public class AuthenticateController {
    @Autowired
    private SecurityServiceInterface securityService;

    @GetMapping("/login")
    public ModelAndView getLogin(HttpSession session){

        if (securityService.isLog(session)) {
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/");
            return new ModelAndView(redirectView);
        }

        Map<String, Object> model = new HashMap<>();
        model.put("page", "login");
        model.put("isLogin", securityService.isLog(session));

        return new ModelAndView("template.html" , model);
    }

    @PostMapping("/login")
    public ModelAndView postLogin(HttpSession session, @RequestParam Map<String,String> requestParams){
        Map<String, String> userLogin = securityService.logUser(requestParams.get("username"), requestParams.get("password"), requestParams.get("remember") != null);

        System.out.println(requestParams.get("remember"));

        if (userLogin != null) {
            userLogin.forEach((k, v) -> session.setAttribute(k, v));
        }

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/");
        return new ModelAndView(redirectView);
    }

    @GetMapping("/signup")
    public ModelAndView getSignup(HttpSession session) throws IOException {

        if (securityService.isLog(session)) {
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/");
            return new ModelAndView(redirectView);
        }

        Map<String, Object> model = new HashMap<>();
        model.put("page", "signup");
        model.put("isLogin", securityService.isLog(session));
        model.put("countries", new CountryService().getAllCountries());

        return new ModelAndView("template.html" , model);
    }

    @PostMapping("/signup")
    public ModelAndView postSignup(HttpSession session, @RequestParam Map<String, Object> requestParams) throws IOException {
        Map<String, String> userLogin = securityService.registerUser(requestParams);

        if (userLogin != null) {
            userLogin.forEach((k, v) -> session.setAttribute(k, v));
        }

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/");
        return new ModelAndView(redirectView);
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session){
        session.invalidate();
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/");
        return new ModelAndView(redirectView);
    }
}
