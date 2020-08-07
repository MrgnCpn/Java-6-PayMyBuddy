package com.paymybuddy.PayMyBuddyWeb.models;

import java.text.DecimalFormat;
import java.time.LocalDate;

public class Transaction {
    private Integer idCard;
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
     * @param idCard
     * @param idFrom
     * @param idTo
     * @param date
     * @param description
     * @param amount
     * @param currency
     */
    public Transaction(Integer idCard, Integer idFrom, Integer idTo, LocalDate date, String description, Double amount, Currency currency) {
        this.idCard = idCard;
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.fee = fee;
        this.currency = currency;
    }

    public Integer getIdCard() {
        return idCard;
    }

    public void setIdCard(Integer fromCard) {
        idCard = fromCard;
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
        this.setFee();
        return Double.valueOf(new DecimalFormat("0.00").format(fee));
    }

    public void setFee() {
        this.fee = Double.valueOf(new DecimalFormat("0.00").format(this.amount * 0.005));
    }

    public Double getFinalAmount() {
        this.setFinalAmount();
        return Double.valueOf(new DecimalFormat("0.00").format(finalAmount));
    }

    public void setFinalAmount() {
        this.setFee();
        this.finalAmount = this.amount - this.fee;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
