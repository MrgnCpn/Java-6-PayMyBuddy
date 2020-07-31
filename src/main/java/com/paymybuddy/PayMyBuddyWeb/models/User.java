package com.paymybuddy.PayMyBuddyWeb.models;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String email;
    private Country country;
    private List<Integer> friends;

    /**
     * Constructor
     * @param id
     * @param firstName
     * @param lastName
     * @param birthday
     * @param email
     * @param country
     */
    public User(Integer id, String firstName, String lastName, LocalDate birthday, String email, Country country) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Integer> getFriends() {
        return friends;
    }

    public void setFriends(List<Integer> friends) {
        this.friends = friends;
    }

    public int getAge(){
        if (this.birthday != null) {
            return Period.between(this.birthday, LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }
}
