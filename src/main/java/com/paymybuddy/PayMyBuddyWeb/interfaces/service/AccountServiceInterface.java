package com.paymybuddy.PayMyBuddyWeb.interfaces.service;

import com.paymybuddy.PayMyBuddyWeb.models.User;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Map;

public interface AccountServiceInterface {

    /**
     * Create new transaction and feed account
     * @param session
     * @param requestParams
     * @param user
     */
    void feedAccount(HttpSession session, Map<String, Object> requestParams, User user) throws SQLException;
}
