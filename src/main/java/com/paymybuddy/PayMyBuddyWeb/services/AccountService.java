package com.paymybuddy.PayMyBuddyWeb.services;

import com.paymybuddy.PayMyBuddyWeb.Utils.MSNumberUtils;
import com.paymybuddy.PayMyBuddyWeb.Utils.MSStringUtils;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.AccountDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.AccountServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.CreditCardServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.SecurityServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.TransactionServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.models.CreditCard;
import com.paymybuddy.PayMyBuddyWeb.models.Transaction;
import com.paymybuddy.PayMyBuddyWeb.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

import static java.lang.Double.parseDouble;

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
                        MSNumberUtils.getDouble_2_digits(amount),
                        user.getAccount().getCurrency()
                );

                transactionService.feedAccountTransaction(transaction);
                user.getAccount().setAmount(MSNumberUtils.getDouble_2_digits(user.getAccount().getAmount() + amount));
                accountDAO.updateAccount(user.getAccount());
            } else {
                logger.error("AccountService.feedAccount : Unknown user card");
            }
        } else {
            logger.error("AccountService.feedAccount : Incomplete transaction");
        }
    }
}
