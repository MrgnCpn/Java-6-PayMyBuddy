package com.paymybuddy.paymybuddyweb.interfaces.dao;

import java.util.List;

/**
 * @author MorganCpn
 */
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

    /**
     * Return is user and friend are friends
     * @param userId
     * @param friendId
     * @return
     */
    Boolean areFriends(Integer userId, Integer friendId);
}
