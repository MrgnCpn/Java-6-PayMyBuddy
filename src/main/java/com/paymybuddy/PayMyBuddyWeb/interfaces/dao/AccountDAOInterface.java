package com.paymybuddy.PayMyBuddyWeb.interfaces.dao;

import com.paymybuddy.PayMyBuddyWeb.models.Account;

public interface AccountDAOInterface {

    /**
     * Get Acoount
     * @param userId
     * @return Account
     */
    Account getAccount(Integer userId);

    /**
     * Upadate Account
     * @param userId
     */
    void updateAccount(Account account);
}
