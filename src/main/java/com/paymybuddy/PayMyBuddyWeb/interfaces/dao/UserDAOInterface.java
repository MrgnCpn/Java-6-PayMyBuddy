package com.paymybuddy.PayMyBuddyWeb.interfaces.dao;

import com.paymybuddy.PayMyBuddyWeb.models.User;

public interface UserDAOInterface {
    /**
     * Get user with id
     * @param id
     * @return User
     */
    User getUserById(Integer id);

    /**
     * Get user with username
     * @param username
     * @return User
     */
    User getUserByUsername(String username);

    /**
     * Update user
     * @param user
     */
    void updateUser(User user);

    /**
     * Create new user
     * @param user
     */
    void createNewUser(User user);
}
