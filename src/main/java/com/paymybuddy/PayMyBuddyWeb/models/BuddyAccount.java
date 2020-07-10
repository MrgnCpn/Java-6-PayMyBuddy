package com.paymybuddy.PayMyBuddyWeb.models;

public class BuddyAccount {
    private Integer userId;
    private Integer amount;
    private Currency currency;

    public BuddyAccount(Integer userId, Integer amount, Currency currency) {
        this.userId = userId;
        this.amount = amount;
        this.currency = currency;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
