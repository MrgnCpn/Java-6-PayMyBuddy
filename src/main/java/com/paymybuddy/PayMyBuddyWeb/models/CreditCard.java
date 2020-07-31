package com.paymybuddy.PayMyBuddyWeb.models;

public class CreditCard {
    private String type;
    private Long number;
    private Integer cvv;
    private String wording;

    /**
     * Constructor
     * @param type
     * @param number
     * @param cvv
     * @param wording
     */
    public CreditCard(String type, Long number, Integer cvv, String wording) {
        this.type = type;
        this.number = number;
        this.cvv = cvv;
        this.wording = wording;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }
}
