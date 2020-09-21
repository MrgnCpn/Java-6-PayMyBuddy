package com.paymybuddy.paymybuddyweb.interfaces.dao;

import com.paymybuddy.paymybuddyweb.models.CreditCard;

import java.util.List;

/**
 * @author MorganCpn
 */
public interface CreditCardDAOInterface {

    /**
     * Get lost of user cards
     * @param userId
     * @return List of CreditCard
     */
    List<CreditCard> getUserCreditCards(Integer userId);

    /**
     * Get card by id
     * @param cardId
     * @param userId
     * @return
     */
    CreditCard getCardById(Integer cardId, Integer userId);

    /**
     * Add new card to user
     * @param creditCard
     */
    void addCreditCard(CreditCard creditCard);

    /**
     * Remove user card
     * @param cardId
     * @param userId
     */
    void removeCreditCard(Integer cardId, Integer userId);

    /**
     * Update user card
     * @param creditCard
     */
    void updateCreditCard(CreditCard creditCard);
}
