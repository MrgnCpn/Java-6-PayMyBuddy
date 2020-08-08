package com.paymybuddy.PayMyBuddyWeb.configuration;

import com.paymybuddy.PayMyBuddyWeb.controllers.ControllerUtil;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.SecurityServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ClassErrorController implements ErrorController {

    @Autowired
    private ControllerUtil controllerUtil;

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @GetMapping("/error")
    @ResponseBody
    public ModelAndView handleError(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        model.put("page", "error");
        model.put("errorCode", request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        model.put("isLogin", controllerUtil.isLog(request.getSession()));

        return new ModelAndView("template.html" , model);
    }
}
