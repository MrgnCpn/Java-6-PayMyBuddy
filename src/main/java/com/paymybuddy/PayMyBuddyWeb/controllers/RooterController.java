package com.paymybuddy.PayMyBuddyWeb.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Singleton;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@Singleton
public class RooterController {
    private final ControllerUtil controllerUtil;

    public RooterController(ControllerUtil controllerUtil) {
        this.controllerUtil = controllerUtil;
    }

    @GetMapping("/")
    public ModelAndView root(HttpSession session) {
        RedirectView redirectView = new RedirectView();
        if (controllerUtil.isLog(session)) {
            redirectView.setUrl("/account");
        } else {
            redirectView.setUrl("/login");
        }
        return new ModelAndView(redirectView);
    }

    @GetMapping("/{page}")
    public ModelAndView getMainRooter(@PathVariable(value="page") String page, HttpSession session){
        Map<String, Object> model = new HashMap<>();
        model.put("page", page);
        model.put("isLogin", controllerUtil.isLog(session));

        if (!controllerUtil.isLog(session)) {
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/login");
            return new ModelAndView(redirectView);
        }

        return new ModelAndView("template.html" , model);
    }
}
