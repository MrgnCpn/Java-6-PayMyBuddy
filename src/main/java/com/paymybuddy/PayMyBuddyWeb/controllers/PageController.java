package com.paymybuddy.PayMyBuddyWeb.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@RestController
@Singleton
public class PageController {
    @GetMapping("/")
    public ModelAndView root(){
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/account");

        return new ModelAndView(redirectView);
    }

    @GetMapping("/login")
    public ModelAndView login(){
        Map<String, Object> model = new HashMap<>();
        model.put("page", "login");
        model.put("isLogin", false);

        return new ModelAndView("template.html" , model);
    }

    @GetMapping("/signup")
    public ModelAndView signup(){
        Map<String, Object> model = new HashMap<>();
        model.put("page", "signup");
        model.put("isLogin", false);

        return new ModelAndView("template.html" , model);
    }

    @GetMapping("/account")
    public ModelAndView account(){
        Map<String, Object> model = new HashMap<>();
        model.put("page", "account");
        model.put("isLogin", true);

        return new ModelAndView("template.html" , model);
    }

    @GetMapping("/account/manageCards/addNewCard")
    public ModelAndView addNewCard(){
        Map<String, Object> model = new HashMap<>();
        model.put("page", "addNewCard");
        model.put("isLogin", true);

        return new ModelAndView("template.html" , model);
    }

    @GetMapping("/transfer")
    public ModelAndView transfer(){
        Map<String, Object> model = new HashMap<>();
        model.put("page", "transfer");
        model.put("isLogin", true);

        return new ModelAndView("template.html" , model);
    }

    @GetMapping("/transfer/passwordCheck")
    public ModelAndView passwordCheck(){
        Map<String, Object> model = new HashMap<>();
        model.put("page", "passwordCheck");
        model.put("isLogin", true);

        return new ModelAndView("template.html" , model);
    }

    @GetMapping("/profile")
    public ModelAndView profile(){
        Map<String, Object> model = new HashMap<>();
        model.put("page", "profile");
        model.put("isLogin", true);

        return new ModelAndView("template.html" , model);
    }

    @GetMapping("/profile/changePassword")
    public ModelAndView changePassword(){
        Map<String, Object> model = new HashMap<>();
        model.put("page", "changePassword");
        model.put("isLogin", true);

        return new ModelAndView("template.html" , model);
    }

    @GetMapping("/contact")
    public ModelAndView contact(){
        Map<String, Object> model = new HashMap<>();
        model.put("page", "contact");
        model.put("isLogin", true);

        return new ModelAndView("template.html" , model);
    }

    @GetMapping("/contact/profile")
    public ModelAndView contactProfile(){
        Map<String, Object> model = new HashMap<>();
        model.put("page", "contactProfile");
        model.put("isLogin", true);

        return new ModelAndView("template.html" , model);
    }
}
