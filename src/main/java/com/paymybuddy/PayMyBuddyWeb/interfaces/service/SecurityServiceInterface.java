package com.paymybuddy.PayMyBuddyWeb.interfaces.service;

import com.paymybuddy.PayMyBuddyWeb.models.User;

import java.util.Map;

public interface SecurityServiceInterface {

    /**
     * Encode user password and insert into db
     * @param userId
     * @param password
     */
    void registerUser(Integer userId, String password);

    /**
     * Check user log and return user profile
     * @param username
     * @param password
     * @return
     */
    Map<String, String> logUser(String username, String password);
}
