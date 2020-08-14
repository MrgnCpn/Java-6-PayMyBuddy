package com.paymybuddy.PayMyBuddyWeb.controllers;

import com.paymybuddy.PayMyBuddyWeb.interfaces.service.CountryServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.SecurityServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Singleton;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Singleton
public class ProfileController {

    @Autowired
    private SecurityServiceInterface securityService;

    @Autowired
    private CountryServiceInterface countryService;

    @Autowired
    private UserServiceInterface userService;

    @GetMapping("/profile")
    public ModelAndView getUserProfile(HttpSession session) throws IOException {
        Map<String, Object> model = new HashMap<>();
        model.put("page", "profile");
        model.put("isLogin", securityService.isLog(session));
        model.put("countries", countryService.getAllCountries());
        model.put("user", userService.getUser(session));

        if (!securityService.isLog(session)) {
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/login");
            return new ModelAndView(redirectView);
        }

        return new ModelAndView("template.html" , model);
    }

    @PostMapping("/profile")
    public ModelAndView postUserProfile(HttpSession session, @RequestParam Map<String, Object> requestParams) throws IOException {
        userService.updateUserProfile(session, requestParams);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/profile");
        return new ModelAndView(redirectView);
    }
}
