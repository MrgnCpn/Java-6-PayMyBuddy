package com.paymybuddy.PayMyBuddyWeb.interfaces.service;

import com.paymybuddy.PayMyBuddyWeb.models.CreditCard;

import javax.servlet.http.HttpSession;

public interface CreditCardServiceInterface {
    /**
     * Get user card by id
     * @param session
     * @param cardId
     * @return
     */
    CreditCard getUserCardById(HttpSession session, Integer cardId);

    /**
     * Remove card
     * @param session
     * @param cardId
     */
    void removeCard(HttpSession session, Integer cardId);
}
