package com.paymybuddy.paymybuddyweb.interfaces.dao;

import com.paymybuddy.paymybuddyweb.models.Account;

public interface AccountDAOInterface {

    /**
     * Get Acoount
     * @param userId
     * @return Account
     */
    Account getAccount(Integer userId);

    /**
     * Upadate Account
     * @param account
     */
    void updateAccount(Account account);

    /**
     * Create new User account
     * @param userId
     * @param currency
     */
    void createAccount(Integer userId, String currency);
}
