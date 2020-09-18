package com.paymybuddy.paymybuddyweb.models;

import com.paymybuddy.paymybuddyweb.utils.MSNumberUtils;

import java.time.LocalDate;

public class Transaction {
    private CreditCard card;
    private User userFrom;
    private User userTo;
    private LocalDate date;
    private String description;
    private Double amount;
    private Double fee;
    private Double finalAmount;
    private Currency currency;

    /**
     * Constructor
     * @param card
     * @param userFrom
     * @param userTo
     * @param date
     * @param description
     * @param amount
     * @param currency
     */
    public Transaction(CreditCard card, User userFrom, User userTo, LocalDate date, String description, Double amount, Currency currency) {
        this.card = card;
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
    }

    public CreditCard getCard() {
        return card;
    }

    public void setCard(CreditCard card) {
        this.card = card;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public User getUserTo() {
        return userTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getFee() {
        this.setFee();
        return MSNumberUtils.getDoubleTwoDigits(fee);
    }

    public void setFee() {
        this.fee = MSNumberUtils.getDoubleTwoDigits(this.amount * 0.005);
    }

    public Double getFinalAmount() {
        this.setFinalAmount();
        return MSNumberUtils.getDoubleTwoDigits(finalAmount);
    }

    public void setFinalAmount() {
        this.setFee();
        this.finalAmount = MSNumberUtils.getDoubleTwoDigits(this.amount - this.fee);
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getHistoryDescription(){
        if (this.getDescription().length() >= 75) {
            return this.getDescription().substring(0,50) + "...";
        }
        return this.getDescription();
    }

    public String getTransactionLib(Integer userId){
        if (this.getUserTo().getId().equals(userId)){
            return "+ " + MSNumberUtils.getDoubleTwoDigits(this.getFinalAmount() * this.getCurrency().getRateBasedUSD()) + " " + this.getCurrency().getSymbol();
        } else if (this.getUserFrom().getId().equals(userId)) {
            return "- " + MSNumberUtils.getDoubleTwoDigits((this.getAmount() + this.getFee()) * this.getCurrency().getRateBasedUSD()) + " " + this.getCurrency().getSymbol();
        } else {
            return null;
        }
    }

    public User getContactUser(Integer userId){
        if (this.getUserTo() != null) {
            if (this.getUserTo().getId().equals(userId)){
                return this.getUserFrom();
            } else if (this.getUserFrom().getId().equals(userId)) {
                return this.getUserTo();
            } else {
                return null;
            }
        }
        return null;
    }


}
