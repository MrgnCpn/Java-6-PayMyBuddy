package com.paymybuddy.PayMyBuddyWeb.models;

import java.time.LocalDate;

public class Account {
    private Double amount;
    private Currency currency;
    private LocalDate date;

    /**
     * Constructor
     * @param amount
     * @param currency
     * @param date
     */
    public Account(Double amount, Currency currency, LocalDate date) {
        this.amount = amount;
        this.currency = currency;
        this.date = date;
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
