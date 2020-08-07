package com.paymybuddy.PayMyBuddyWeb.models;

public class CreditCard {
    private Integer cardId;
    private Integer userId;
    private String type;
    private String number;
    private String cvv;
    private String wording;

    /**
     * Constructor
     * @param type
     * @param number
     * @param cvv
     * @param wording
     */
    public CreditCard(Integer cardId, Integer userId, String type, String number, String cvv, String wording) {
        this.cardId = cardId;
        this.userId = userId;
        this.type = type;
        this.number = number;
        this.cvv = cvv;
        this.wording = wording;
    }

    /**
     * Constructor
     */
    public CreditCard() {

    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }
}
