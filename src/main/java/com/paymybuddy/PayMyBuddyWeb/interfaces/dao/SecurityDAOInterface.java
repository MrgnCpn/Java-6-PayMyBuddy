package com.paymybuddy.paymybuddyweb.interfaces.dao;

public interface SecurityDAOInterface {

    /**
     * Get user password
     * @param username
     * @return
     */
    String getUserPassword(String username);

    /**
     * Update user password
     * @param userId
     * @param userPassword
     */
    void updatePassword(Integer userId, String userPassword);
}
