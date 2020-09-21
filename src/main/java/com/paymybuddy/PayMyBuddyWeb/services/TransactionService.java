package com.paymybuddy.paymybuddyweb.services;

import com.paymybuddy.paymybuddyweb.utils.MSStringUtils;
import com.paymybuddy.paymybuddyweb.interfaces.dao.TransactionDAOInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.SecurityServiceInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.TransactionServiceInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.UserServiceInterface;
import com.paymybuddy.paymybuddyweb.models.Currency;
import com.paymybuddy.paymybuddyweb.models.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author MorganCpn
 */
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
     * User Service
     */
    private final UserServiceInterface userService;

    /**
     * Constructor
     * @param securityService
     * @param transactionDAO
     */
    public TransactionService(SecurityServiceInterface securityService, TransactionDAOInterface transactionDAO, UserServiceInterface userService) {
        this.securityService = securityService;
        this.transactionDAO = transactionDAO;
        this.userService = userService;
    }

    /**
     * @see TransactionServiceInterface {@link #getUserTransactions(HttpSession)}
     */
    @Override
    public List<Transaction> getUserTransactions(HttpSession session) {
        List<Transaction> transactionList = null;
        Map<String, Object> userInfo = securityService.getUserInfoFromJWT(session);
        if (userInfo != null) {
            transactionList = transactionDAO.getUserTransactions((Integer) userInfo.get("userID"));
        }
        return transactionList;
    }

    /**
     * @see TransactionServiceInterface {@link #feedAccountTransaction(Transaction)}
     */
    @Override
    public void feedAccountTransaction(Transaction transaction) throws SQLException {
        transactionDAO.newTransaction(transaction);
    }

    /**
     * @see TransactionServiceInterface {@link #doTransfer(HttpSession, Map)}
     */
    public void doTransfer(HttpSession session, Map<String, Object> requestParams) throws SQLException, IOException {
        Map <String, Object> userInfo = securityService.getUserInfoFromJWT(session);
        if (    userInfo != null &&
                requestParams != null &&
                !MSStringUtils.isEmpty((String) requestParams.get("to")) &&
                !MSStringUtils.isEmpty((String) requestParams.get("amount")) &&
                !MSStringUtils.isEmpty((String) requestParams.get("currency")) &&
                !MSStringUtils.isEmpty((String) requestParams.get("description")) &&
                userService.getIfAreFriends(session, Integer.valueOf((String) requestParams.get("to")))
        ) {
            transactionDAO.newTransaction(
                    new Transaction(
                        null,
                        userService.getUser((Integer) userInfo.get("userID")),
                        userService.getUser(Integer.valueOf((String) requestParams.get("to"))),
                        LocalDate.now(),
                        (String) requestParams.get("description"),
                        Double.valueOf((String) requestParams.get("amount")),
                        new Currency((String) requestParams.get("currency"))
                )
            );
        } else {
            logger.error("TransactionService.doTransfer : Incomplete transaction");
        }
    }
}
