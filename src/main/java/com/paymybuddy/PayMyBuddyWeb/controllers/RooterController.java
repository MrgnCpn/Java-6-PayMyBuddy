package com.paymybuddy.paymybuddyweb.controllers;

import com.paymybuddy.paymybuddyweb.interfaces.Utils.ControllerUtilsInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.SecurityServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Singleton;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@Singleton
public class RooterController {

    @Autowired
    private SecurityServiceInterface securityService;

    @Autowired
    private ControllerUtilsInterface controllerUtils;

    @GetMapping("/")
    public ModelAndView root(HttpSession session) {
        if (securityService.isLog(session)) {
            return controllerUtils.doRedirect("/account");
        } else {
            return controllerUtils.loginRedirect();
        }
    }

    @GetMapping("/{page}")
    public ModelAndView getMainRooter(@PathVariable(value="page") String page, HttpSession session){
        if (!securityService.isLog(session)) return controllerUtils.loginRedirect();

        Map<String, Object> model = new HashMap<>();
        model.put("page", page);
        model.put("isLogin", securityService.isLog(session));

        return new ModelAndView("template.html" , model);
    }
}
