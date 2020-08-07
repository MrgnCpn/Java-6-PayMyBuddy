package com.paymybuddy.PayMyBuddyWeb.models;

import java.time.LocalDate;

public class Account {
    private Integer userId;
    private Double amount;
    private Currency currency;
    private LocalDate date;

    /**
     * Constructor
     */
    public Account() { }

    /**
     * Constructor
     * @param amount
     * @param currency
     * @param date
     */
    public Account(Integer userId, Double amount, Currency currency, LocalDate date) {
        this.userId = userId;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}