package com.paymybuddy.PayMyBuddyWeb.interfaces.dao;

import com.paymybuddy.PayMyBuddyWeb.models.Transaction;

import java.util.List;

public interface TransactionDAOInterface {
    /**
     * Get all user transaction
     * @param userId
     * @return List of Transaction
     */
    List<Transaction> getUserTransactions(Integer userId);

    /**
     * Get All transaction between 2 users
     * @param userId_1
     * @param userId_2
     * @return List of Transaction
     */
    List<Transaction> getTransactionsBetweenTwoUsers(Integer userId_1, Integer userId_2);

    /**
     * Create new transaction
     * @param transaction
     */
    void newTransaction(Transaction transaction);
}
