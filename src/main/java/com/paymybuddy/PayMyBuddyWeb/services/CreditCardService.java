package com.paymybuddy.PayMyBuddyWeb.services;

import com.paymybuddy.PayMyBuddyWeb.Utils.MSStringUtils;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.CreditCardDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.CreditCardServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.SecurityServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.models.CreditCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Singleton
public class CreditCardService implements CreditCardServiceInterface {
    /**
     * Logger log4j2
     */
    private static final Logger logger = LogManager.getLogger("CreditCardService");

    /**
     * Credit card DAO
     */
    private final CreditCardDAOInterface creditCardDAO;

    /**
     * Security Service
     */
    private final SecurityServiceInterface securityService;

    /**
     * Constructor
     * @param creditCardDAO
     * @param securityService
     */
    public CreditCardService(CreditCardDAOInterface creditCardDAO, SecurityServiceInterface securityService) {
        this.creditCardDAO = creditCardDAO;
        this.securityService = securityService;
    }

    /**
     * @see CreditCardServiceInterface {@link #getUserCardById(HttpSession, Integer)}
     */
    @Override
    public CreditCard getUserCardById(HttpSession session, Integer cardId) {
        CreditCard card = null;
        Map<String, Object> userInfo = securityService.getUserInfoFromJWT(session);
        if (userInfo != null) {
            card = creditCardDAO.getCardById(cardId, (Integer) userInfo.get("userID"));
        }
        return card;
    }

    /**
     * @see CreditCardServiceInterface {@link #removeCard(HttpSession, Integer)}
     */
    @Override
    public void removeCard(HttpSession session, Integer cardId) {
        Map<String, Object> userInfo = securityService.getUserInfoFromJWT(session);
        if (userInfo != null) {
            creditCardDAO.removeCreditCard(cardId, (Integer) userInfo.get("userID"));
        }
    }

    /**
     * @see CreditCardServiceInterface {@link #getCardFromRequestParam(HttpSession, Integer, Map)}
     */
    @Override
    public CreditCard getCardFromRequestParam(HttpSession session, Integer cardId, Map<String, Object> requestParams) {
        CreditCard card = null;
        Map <String, Object> userInfo = securityService.getUserInfoFromJWT(session);
        if (    userInfo != null &&
                requestParams != null &&
                !MSStringUtils.isEmpty((String) requestParams.get("wording")) &&
                !MSStringUtils.isEmpty((String) requestParams.get("cardNumber_1")) &&
                !MSStringUtils.isEmpty((String) requestParams.get("cardNumber_2")) &&
                !MSStringUtils.isEmpty((String) requestParams.get("cardNumber_3")) &&
                !MSStringUtils.isEmpty((String) requestParams.get("cardNumber_4")) &&
                !MSStringUtils.isEmpty((String) requestParams.get("cvv")) &&
                !MSStringUtils.isEmpty((String) requestParams.get("date")) &&
                !MSStringUtils.isEmpty((String) requestParams.get("card_type"))
        ) {
            card = new CreditCard(
                    cardId,
                    (Integer) userInfo.get("userID"),
                    (String) requestParams.get("card_type"),
                    (String) requestParams.get("cardNumber_1") +
                            requestParams.get("cardNumber_2") +
                            requestParams.get("cardNumber_3") +
                            requestParams.get("cardNumber_4"),
                    (String) requestParams.get("cvv"),
                    (String) requestParams.get("date"),
                    (String) requestParams.get("wording")
            );
        }
        return card;
    }

    /**
     * @see CreditCardServiceInterface {@link #updateCard(HttpSession, Integer, Map)}
     */
    @Override
    public void updateCard(HttpSession session, Integer cardId, Map<String, Object> requestParams) {
        CreditCard card = getCardFromRequestParam(session, cardId, requestParams);
        if(card != null) {
            creditCardDAO.updateCreditCard(card);
        } else {
            logger.error("CreditCardService.updateCard : Incomplete card");
        }
    }

    /**
     * @see CreditCardServiceInterface {@link #addNewCard(HttpSession, Map)}
     */
    @Override
    public void addNewCard(HttpSession session, Map<String, Object> requestParams) {
       CreditCard card = getCardFromRequestParam(session, null, requestParams);
       if(card != null) {
            creditCardDAO.addCreditCard(card);
        } else {
            logger.error("CreditCardService.addNewCard : Incomplete new card");
        }
    }
}
