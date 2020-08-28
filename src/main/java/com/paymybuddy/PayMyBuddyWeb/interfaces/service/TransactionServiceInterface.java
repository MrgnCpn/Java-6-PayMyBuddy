package com.paymybuddy.PayMyBuddyWeb.interfaces.service;

import com.paymybuddy.PayMyBuddyWeb.models.Transaction;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface TransactionServiceInterface {

    /**
     * Get user transactions
     * @param session
     * @return
     */
    List<Transaction> getUserTransaction(HttpSession session);

    /**
     * Create Transaction
     * @param transaction
     */
    void feedAccountTransaction(Transaction transaction) throws SQLException;

    /**
     * Create transaction in transfer
     * @param session
     * @param requestParams
     * @throws SQLException
     * @throws IOException
     */
    void doTransfer(HttpSession session, Map<String, Object> requestParams) throws SQLException, IOException;
}