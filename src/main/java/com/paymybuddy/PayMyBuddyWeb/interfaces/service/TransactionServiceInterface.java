package com.paymybuddy.PayMyBuddyWeb.interfaces.service;

import com.paymybuddy.PayMyBuddyWeb.models.Transaction;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface TransactionServiceInterface {

    /**
     * Get user transactions
     * @param session
     * @return
     */
    List<Transaction> getUserTransaction(HttpSession session);
}
