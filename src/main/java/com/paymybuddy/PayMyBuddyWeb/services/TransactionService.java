package com.paymybuddy.PayMyBuddyWeb.services;

import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.TransactionDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.SecurityServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.TransactionServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.models.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Singleton
public class TransactionService implements TransactionServiceInterface {
    /**
     * Logger log4j2
     */
    private static final Logger logger = LogManager.getLogger("TransactionService");

    /**
     * Security Service
     */
    private final SecurityServiceInterface securityService;

    /**
     * TransactionDAO
     */
    private final TransactionDAOInterface transactionDAO;

    /**
     * Constructor
     * @param securityService
     * @param transactionDAO
     */
    public TransactionService(SecurityServiceInterface securityService, TransactionDAOInterface transactionDAO) {
        this.securityService = securityService;
        this.transactionDAO = transactionDAO;
    }

    public List<Transaction> getUserTransaction(HttpSession session) {
        List<Transaction> transactionList = null;
        Map<String, Object> userInfo = securityService.getUserInfoFromJWT(session);
        if (userInfo != null) {
            transactionList = transactionDAO.getUserTransactions((Integer) userInfo.get("userID"));
        }
        return transactionList;
    }
}
