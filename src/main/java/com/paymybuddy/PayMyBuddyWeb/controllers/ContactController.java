package com.paymybuddy.paymybuddyweb.controllers;

import com.paymybuddy.paymybuddyweb.interfaces.Utils.ControllerUtilsInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.SecurityServiceInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.UserServiceInterface;
import com.paymybuddy.paymybuddyweb.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Singleton;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MorganCpn
 */
@RestController
@Singleton
public class ContactController {
    @Autowired
    private ControllerUtilsInterface controllerUtils;

    @Autowired
    private SecurityServiceInterface securityService;

    @Autowired
    private UserServiceInterface userService;

    @GetMapping("/contact")
    public ModelAndView getUserProfile(HttpSession session) {
        if (!securityService.isLog(session)) return controllerUtils.rootRedirect();

        Map<String, Object> model = new HashMap<>();
        User user = userService.getUser(session);
        model.put("page", "contact");
        model.put("isLogin", securityService.isLog(session));
        model.put("friends", userService.getUserFriends(user));

        return new ModelAndView("template.html" , model);
    }

    @GetMapping("/contact/profile/{id}")
    public ModelAndView getUserProfile(HttpSession session, @PathVariable(value="id") Integer friendId) {
        if (!securityService.isLog(session)) return controllerUtils.rootRedirect();

        User user = userService.getUser(friendId);
        Map<String, Object> model = new HashMap<>();
        model.put("page", "contact-profile");
        model.put("isLogin", securityService.isLog(session));
        model.put("user", user);
        model.put("friendship", userService.getIfAreFriends(session, friendId));

        return new ModelAndView("template.html" , model);
    }


    @GetMapping("/contact/friends/remove")
    public ModelAndView removeFriend(HttpSession session, @RequestParam(required = true) Integer id, @RequestParam(required = true) String from) {
        if (!securityService.isLog(session)) return controllerUtils.rootRedirect();
        userService.removeFriend(session, id);

        if (from.equals("/contact")) {
            return controllerUtils.doRedirect(from);
        } else {
            return controllerUtils.doRedirect(from + id);
        }
    }

    @GetMapping("/contact/friends/add")
    public ModelAndView addFriend(HttpSession session, @RequestParam(required = true) Integer id, @RequestParam(required = true) String from) {
        if (!securityService.isLog(session)) return controllerUtils.rootRedirect();
        userService.addFriend(session, id);

        if (from.equals("/contact")) {
            return controllerUtils.doRedirect(from);
        } else {
            return controllerUtils.doRedirect(from + id);
        }
    }

    @GetMapping("/contact/friends/search")
    public String searchContact(HttpSession session, @RequestParam(required = true) String search) {
        return userService.searchContactUsers(session, search);
    }
}
