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

    @GetMapping("/")
    public ModelAndView root(){
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/login");

        return new ModelAndView(redirectView);
    }

    @GetMapping("/{page}")
    public ModelAndView rooter(@PathVariable(value="page") String page){
        Map<String, Object> model = new HashMap<>();
        model.put("page", page);
        model.put("isLogin", true);

        return new ModelAndView("template.html" , model);
    }
}
