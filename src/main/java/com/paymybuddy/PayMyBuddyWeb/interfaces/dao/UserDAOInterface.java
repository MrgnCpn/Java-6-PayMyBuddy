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
     * Update user
     * @param user
     */
    void updateUser(User user);

    /**
     * Create new user
     * @param user
     */
    void insertUser(User user);
}
