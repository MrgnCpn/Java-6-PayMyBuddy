package com.paymybuddy.paymybuddyweb.utils;

import com.paymybuddy.paymybuddyweb.interfaces.Utils.ControllerUtilsInterface;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author MorganCpn
 */
public class MSControllerUtils implements ControllerUtilsInterface {

    public MSControllerUtils(){}

    /**
     * @see ControllerUtilsInterface {@link #redirect(String)}
     */
    public RedirectView redirect(String path) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(path);
        return redirectView;
    }

    /**
     * @see ControllerUtilsInterface {@link #loginRedirect()}
     */
    public ModelAndView loginRedirect() {
        return new ModelAndView(redirect("/login"));
    }

    /**
     * @see ControllerUtilsInterface {@link #rootRedirect()}
     */
    public ModelAndView rootRedirect(){
        return new ModelAndView(redirect("/"));
    }

    /**
     * @see ControllerUtilsInterface {@link #doRedirect(String)}
     */
    public ModelAndView doRedirect(String path) {
        if (!MSStringUtils.isEmpty(path)) {
            return new ModelAndView(redirect(path));
        } else {
            throw new NullPointerException("ControllerUtils.doRedirect() -> Path empty");
        }
    }
}
