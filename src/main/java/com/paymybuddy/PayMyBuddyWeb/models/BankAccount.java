package com.paymybuddy.PayMyBuddyWeb.models;

public class BankAccount {
    private Integer userId;
    private String type;
    private Long cardNumber;
    private Integer cardCryptogramme;
    private String iban;
    private String bic;
    private String wording;

    public BankAccount(Integer userId, String type, Long cardNumber, Integer cardCryptogramme, String iban, String bic, String wording) {
        this.userId = userId;
        this.type = type;
        this.cardNumber = cardNumber;
        this.cardCryptogramme = cardCryptogramme;
        this.iban = iban;
        this.bic = bic;
        this.wording = wording;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getCardCryptogramme() {
        return cardCryptogramme;
    }

    public void setCardCryptogramme(Integer cardCryptogramme) {
        this.cardCryptogramme = cardCryptogramme;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }
}