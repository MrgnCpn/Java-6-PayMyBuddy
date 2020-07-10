package com.paymybuddy.PayMyBuddyWeb.configuration;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ClassErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @GetMapping("/error")
    @ResponseBody
    public String handleError(HttpServletRequest request) {
        return "Error : " + request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    }
}
