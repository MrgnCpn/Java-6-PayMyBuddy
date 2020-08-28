package com.paymybuddy.PayMyBuddyWeb.models;

import java.util.ArrayList;
import java.util.List;

public class CreditCard {
    private Integer id;
    private Integer userId;

    // MAST - VISA
    private String type;
    private String number;
    private String cvv;
    private String date;
    private String wording;

    /**
     * Constructor
     * @param type
     * @param number
     * @param cvv
     * @param wording
     */
    public CreditCard(Integer id, Integer userId, String type, String number, String cvv, String date, String wording) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.number = number;
        this.cvv = cvv;
        this.date = date;
        this.wording = wording;
    }

    /**
     * Constructor
     */
    public CreditCard() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer cardId) {
        this.id = cardId;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    public List<String> getNumberSplited(){
        List<String> numbers = new ArrayList<>();
        numbers.add(this.getNumber().substring(0,4));
        numbers.add(this.getNumber().substring(4,8));
        numbers.add(this.getNumber().substring(8,12));
        numbers.add(this.getNumber().substring(12,16));
        return numbers;
    }

    public List<String> getNumberHide(){
        List<String> numbers = new ArrayList<>();
        numbers.add("****");
        numbers.add("****");
        numbers.add("****");
        numbers.add(this.getNumber().substring(12,16));
        return numbers;
    }

    public String getWordingLib(){
        if (this.getWording().length() > 15) return this.getWording().substring(0, 15) + "...";
        else return this.getWording();
    }
}
