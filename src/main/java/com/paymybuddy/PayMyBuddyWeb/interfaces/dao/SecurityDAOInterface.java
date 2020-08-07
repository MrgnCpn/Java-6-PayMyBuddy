package com.paymybuddy.PayMyBuddyWeb.interfaces.dao;

public interface SecurityDAOInterface {

    /**
     * Get user password
     * @param userId
     * @return
     */
    String getUserPassword(Integer userId);

    /**
     * Update user password
     * @param userId
     * @param userPassword
     */
    void updatePassword(Integer userId, String userPassword);
}
