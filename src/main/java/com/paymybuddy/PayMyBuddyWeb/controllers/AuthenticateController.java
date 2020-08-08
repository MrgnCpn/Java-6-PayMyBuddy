package com.paymybuddy.PayMyBuddyWeb.controllers;

import com.paymybuddy.PayMyBuddyWeb.interfaces.service.SecurityServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Singleton;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@Singleton
public class AuthenticateController {

    @Autowired
    private ControllerUtil controllerUtil;

    @Autowired
    private SecurityServiceInterface securityService;

    @GetMapping("/login")
    public ModelAndView getLogin(HttpSession session){
        Map<String, Object> model = new HashMap<>();
        model.put("page", "login");
        model.put("isLogin", controllerUtil.isLog(session));

        return new ModelAndView("template.html" , model);
    }

    /*
    @PostMapping("/login")
    public ModelAndView postLogin(HttpSession session, @RequestBody Map<String,String> requestParams){
        Map<String, String> userLogin = securityService.logUser(requestParams.get("username"), requestParams.get("password"));

        if (userLogin != null) {
            userLogin.forEach((k, v) -> session.setAttribute(k, v));
        }

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/");
        return new ModelAndView(redirectView);
    }
    */


    @PostMapping("/login")
    public String postLogin(HttpSession session, @RequestBody Map<String,String> requestParams){
        Map<String, String> userLogin = securityService.logUser(requestParams.get("username"), requestParams.get("password"));
        String r = "TEST \n";
        if (userLogin != null) {
            userLogin.forEach((k, v) -> session.setAttribute(k, v));
            for (Map.Entry<String, String> entry : userLogin.entrySet()) {
                r += "Key : " + entry.getKey() + " / " + "Value : " + entry.getValue() + "\n";
            }
        }
        return r;
    }



    @GetMapping("/signup")
    public ModelAndView getSignup(HttpSession session){
        Map<String, Object> model = new HashMap<>();
        model.put("page", "signup");
        model.put("isLogin", controllerUtil.isLog(session));

        return new ModelAndView("template.html" , model);
    }

    @PostMapping("/signup")
    public ModelAndView postSignup(HttpSession session, @RequestBody Map<String,String> requestParams){
        //

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/");
        return new ModelAndView(redirectView);
    }
}
