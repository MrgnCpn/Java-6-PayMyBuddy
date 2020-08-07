package com.paymybuddy.PayMyBuddyWeb.interfaces.dao;

import java.util.List;

public interface FriendDAOInterface {

    /**
     * Get all user friends
     * @param userId
     * @return
     */
    List<Integer> getUserFriends(Integer userId);

    /**
     * Add friend to user
     * @param userId
     * @param friendId
     */
    void addNewFriendToUser(Integer userId, Integer friendId);

    /**
     * Remove user friend
     * @param userId
     * @param friendId
     */
    void removeFriendFromUser(Integer userId, Integer friendId);
}
