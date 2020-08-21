package com.paymybuddy.PayMyBuddyWeb.services;

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
}
