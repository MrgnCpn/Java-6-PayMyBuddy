package com.paymybuddy.PayMyBuddyWeb.controllers;

import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.servlet.http.HttpSession;

@Component
@Singleton
public class ControllerUtil {
    public boolean isLog(HttpSession session) {
        return (session.getAttribute("userId") != null) && (session.getAttribute("username") != null);
    }
}
