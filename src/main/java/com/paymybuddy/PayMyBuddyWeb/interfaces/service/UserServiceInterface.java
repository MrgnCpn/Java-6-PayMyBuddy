package com.paymybuddy.PayMyBuddyWeb.interfaces.service;

import com.paymybuddy.PayMyBuddyWeb.models.User;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserServiceInterface {

    /**
     * Get user with token information
     * @param session
     * @return
     */
    User getUser(HttpSession session);

    /**
     * Get One profile by id
     * @param id
     * @return
     */
    User getUser(Integer id);

    /**
     * Get all user friends profile
     * @param user
     * @return
     */
    List<User> getUserFriends(User user);

    /**
     * Upadte User profile
     * @param session
     * @param requestParams
     * @throws IOException
     */
    void updateUserProfile(HttpSession session, Map<String, Object> requestParams) throws IOException;

    /**
     * Get if user and friend are friends
     * @param session
     * @param friendId
     * @return
     */
    Boolean getIfAreFriends(HttpSession session, Integer friendId);

    /**
     * Remove friend
     * @param session
     * @param friendId
     */
    void removeFriend(HttpSession session, Integer friendId);

    /**
     * Add friend
     * @param session
     * @param friendId
     */
    void addFriend(HttpSession session, Integer friendId);

    /**
     * Get list of users with search
     * @param session
     * @param search
     * @return JSON structure
     */
    String searchContactUsers(HttpSession session, String search);
}
