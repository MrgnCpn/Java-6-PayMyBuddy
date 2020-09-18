package com.paymybuddy.paymybuddyweb.interfaces.service;

import com.paymybuddy.paymybuddyweb.models.CreditCard;

import javax.servlet.http.HttpSession;
import java.util.Map;

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

    /**
     * Update Card
     * @param session
     * @param cardId
     * @param requestParams
     */
    void updateCard(HttpSession session, Integer cardId, Map<String, Object> requestParams);

    /**
     * Add new card
     * @param session
     * @param requestParams
     */
    void addNewCard(HttpSession session, Map<String, Object> requestParams);

    /**
     * Parse Request session, check values and return card
     * @param session
     * @param cardId
     * @param requestParams
     * @return
     */
    CreditCard getCardFromRequestParam(HttpSession session, Integer cardId, Map<String, Object> requestParams);
}
