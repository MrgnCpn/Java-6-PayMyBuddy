package com.paymybuddy.paymybuddyweb.controllers;

import com.paymybuddy.paymybuddyweb.interfaces.Utils.ControllerUtilsInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.CountryServiceInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.SecurityServiceInterface;
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
import java.util.HashMap;
import java.util.Map;

/**
 * @author MorganCpn
 */
@RestController
@Singleton
public class ProfileController {

    @Autowired
    private SecurityServiceInterface securityService;

    @Autowired
    private CountryServiceInterface countryService;

    @Autowired
    private UserServiceInterface userService;

    @Autowired
    private ControllerUtilsInterface controllerUtils;

    @GetMapping("/profile")
    public ModelAndView getUserProfile(HttpSession session) throws IOException {
        if (!securityService.isLog(session)) return controllerUtils.rootRedirect();

        User user = userService.getUser(session);
        Map<String, Object> model = new HashMap<>();
        model.put("page", "profile");
        model.put("isLogin", securityService.isLog(session));
        model.put("countries", countryService.getAllCountries());
        model.put("user", user);
        return new ModelAndView("template.html" , model);
    }

    @PostMapping("/profile")
    public ModelAndView postUserProfile(HttpSession session, @RequestParam(required = true) Map<String, Object> requestParams) throws IOException {
        userService.updateUserProfile(session, requestParams);
        return controllerUtils.doRedirect("/profile");
    }
}
