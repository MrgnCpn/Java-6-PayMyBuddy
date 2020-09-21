package com.paymybuddy.paymybuddyweb.services;

import com.paymybuddy.paymybuddyweb.utils.MSNumberUtils;
import com.paymybuddy.paymybuddyweb.utils.MSStringUtils;
import com.paymybuddy.paymybuddyweb.interfaces.dao.AccountDAOInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.AccountServiceInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.CreditCardServiceInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.SecurityServiceInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.TransactionServiceInterface;
import com.paymybuddy.paymybuddyweb.models.CreditCard;
import com.paymybuddy.paymybuddyweb.models.Transaction;
import com.paymybuddy.paymybuddyweb.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

import static java.lang.Double.parseDouble;

/**
 * @author MorganCpn
 */
@Singleton
public class AccountService implements AccountServiceInterface {
    /**
     * Logger log4j2
     */
    private static final Logger logger = LogManager.getLogger("AccountService");

    /**
     * Security Service
     */
    private final SecurityServiceInterface securityService;

    /**
     * Credit card DAO
     */
    private final CreditCardServiceInterface creditCardService;

    /**
     * Transaction Service
     */
    private TransactionServiceInterface transactionService;


    /**
     * Account DAO
     */
    private AccountDAOInterface accountDAO;

    /**
     * Constructor
     * @param securityService
     * @param creditCardService
     * @param transactionService
     * @param accountDAO
     */
    public AccountService(SecurityServiceInterface securityService, CreditCardServiceInterface creditCardService, TransactionServiceInterface transactionService, AccountDAOInterface accountDAO) {
        this.securityService = securityService;
        this.creditCardService = creditCardService;
        this.transactionService = transactionService;
        this.accountDAO = accountDAO;
    }

    /**
     * @see AccountServiceInterface {@link #feedAccount(HttpSession, Map, User)}
     */
    public void feedAccount(HttpSession session, Map<String, Object> requestParams, User user) throws SQLException {
        Map <String, Object> userInfo = securityService.getUserInfoFromJWT(session);
        if (    userInfo != null &&
                requestParams != null &&
                !MSStringUtils.isEmpty((String) requestParams.get("cardid")) &&
                !MSStringUtils.isEmpty((String) requestParams.get("amount"))
        ) {
            CreditCard card = creditCardService.getUserCardById(session, (Integer) userInfo.get("userID"));
            Double amount = parseDouble((String) requestParams.get("amount")) / user.getAccount().getCurrency().getRateBasedUSD();
            if ((card != null) && (amount > 0)) {
                Transaction transaction = new Transaction(
                        card,
                        null,
                        user,
                        LocalDate.now(),
                        "Feed Account",
                        MSNumberUtils.getDoubleTwoDigits(amount),
                        user.getAccount().getCurrency()
                );

                transactionService.feedAccountTransaction(transaction);
                user.getAccount().setAmount(MSNumberUtils.getDoubleTwoDigits(user.getAccount().getAmount() + amount));
                accountDAO.updateAccount(user.getAccount());
            } else {
                logger.error("AccountService.feedAccount : Unknown user card");
            }
        } else {
            logger.error("AccountService.feedAccount : Incomplete transaction");
        }
    }
}
