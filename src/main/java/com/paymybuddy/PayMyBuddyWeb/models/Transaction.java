package com.paymybuddy.PayMyBuddyWeb.models;

import java.time.LocalDate;

public class Transaction {
    private Boolean isFromCard;
    private Integer idFrom;
    private Integer idTo;
    private LocalDate date;
    private String description;
    private Double amount;
    private Double fee;
    private Double finalAmount;
    private Currency currency;

    /**
     * Constructor
     * @param isFromCard
     * @param idFrom
     * @param idTo
     * @param date
     * @param description
     * @param amount
     * @param fee
     * @param currency
     */
    public Transaction(Boolean isFromCard, Integer idFrom, Integer idTo, LocalDate date, String description, Double amount, Double fee, Currency currency) {
        this.isFromCard = isFromCard;
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.fee = fee;
        this.currency = currency;
    }

    public Boolean getFromCard() {
        return isFromCard;
    }

    public void setFromCard(Boolean fromCard) {
        isFromCard = fromCard;
    }

    public Integer getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(Integer idFrom) {
        this.idFrom = idFrom;
    }

    public Integer getIdTo() {
        return idTo;
    }

    public void setIdTo(Integer idTo) {
        this.idTo = idTo;
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
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(Double finalAmount) {
        this.finalAmount = this.amount + (this.amount * this.fee);
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
