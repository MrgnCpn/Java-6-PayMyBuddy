package com.paymybuddy.paymybuddyweb.interfaces.service;

import com.paymybuddy.paymybuddyweb.models.User;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author MorganCpn
 */
public interface AccountServiceInterface {

    /**
     * Create new transaction and feed account
     * @param session
     * @param requestParams
     * @param user
     */
    void feedAccount(HttpSession session, Map<String, Object> requestParams, User user) throws SQLException;
}
