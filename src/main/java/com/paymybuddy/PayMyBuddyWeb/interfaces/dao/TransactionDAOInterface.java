package com.paymybuddy.PayMyBuddyWeb.interfaces.dao;

import com.paymybuddy.PayMyBuddyWeb.models.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface TransactionDAOInterface {
    /**
     * Get all user transaction
     * @param userId
     * @return List of Transaction
     */
    List<Transaction> getUserTransactions(Integer userId);

    /**
     * Create new transaction
     * @param transaction
     */
    void newTransaction(Transaction transaction) throws SQLException;
}
