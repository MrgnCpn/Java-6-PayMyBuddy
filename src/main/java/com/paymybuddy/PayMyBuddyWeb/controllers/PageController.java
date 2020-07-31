package com.paymybuddy.PayMyBuddyWeb.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@RestController
@Singleton
public class PageController {
    boolean isLogin = true;

    @GetMapping("/")
    public ModelAndView root(){
        RedirectView redirectView = new RedirectView();
        if (isLogin) {
            redirectView.setUrl("/account");
        } else {
            redirectView.setUrl("/login");
        }

        return new ModelAndView(redirectView);
    }

    @GetMapping("/login")
    public ModelAndView login(){
        Map<String, Object> model = new HashMap<>();
        model.put("page", "login");
        model.put("isLogin", isLogin);

        return new ModelAndView("template.html" , model);
    }

    @GetMapping("/signup")
    public ModelAndView signup(){
        Map<String, Object> model = new HashMap<>();
        model.put("page", "signup");
        model.put("isLogin", isLogin);

        return new ModelAndView("template.html" , model);
    }

    @GetMapping("/{page}")
    public ModelAndView getRooter(@PathVariable(value="page") String page){
        Map<String, Object> model = new HashMap<>();
        model.put("page", page);
        model.put("isLogin", isLogin);

        if (!isLogin) {
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/login");
            return new ModelAndView(redirectView);
        }

        return new ModelAndView("template.html" , model);
    }
}
