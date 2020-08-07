package com.paymybuddy.PayMyBuddyWeb.interfaces.dao;

import com.paymybuddy.PayMyBuddyWeb.models.CreditCard;

import java.util.List;

public interface CreditCardDAOInterface {

    /**
     * Get lost of user cards
     * @param userId
     * @return List of CreditCard
     */
    List<CreditCard> getUserCreditCards(Integer userId);

    /**
     * Add new card to user
     * @param creditCard
     */
    void addCreditCard(CreditCard creditCard);

    /**
     * Remove user card
     * @param credtCard
     */
    void removeCreditCard(CreditCard credtCard);

    /**
     * Update user card
     * @param creditCard
     */
    void updateCreditCard(CreditCard creditCard);
}
