package com.paymybuddy.paymybuddyweb.interfaces.Utils;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author MorganCpn
 */
public interface ControllerUtilsInterface {
    /**
     * Return RedirectVien
     * @param path
     * @return
     */
    RedirectView redirect(String path);

    /**
     * Do redirect to "/login"
     * @return
     */
    ModelAndView loginRedirect();

    /**
     * Do redirect to "/"
     * @return
     */
    ModelAndView rootRedirect();

    /**
     * Do redirect to selected path
     * @param path
     * @return
     * @throws NullPointerException
     */
    ModelAndView doRedirect(String path) throws NullPointerException;
}
