package com.paymybuddy.PayMyBuddyWeb.interfaces.service;

import com.paymybuddy.PayMyBuddyWeb.models.User;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public interface UserServiceInterface {

    /**
     * Get user with token information
     * @param session
     * @return
     */
    User getUser(HttpSession session);

    /**
     * Upadte User profile
     * @param session
     * @param requestParams
     * @throws IOException
     */
    void updateUserProfile(HttpSession session, Map<String, Object> requestParams) throws IOException;
}
