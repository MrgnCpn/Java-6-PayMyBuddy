package com.paymybuddy.PayMyBuddyWeb.models;

import com.paymybuddy.PayMyBuddyWeb.Utils.MSFileUtils;
import com.paymybuddy.PayMyBuddyWeb.Utils.MSStringUtils;

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
    private Account account;
    private List<CreditCard> creditCards;

    /**
     * Constructor
     * @param id
     * @param firstName
     * @param lastName
     * @param birthday
     * @param email
     * @param country
     * @param friends
     */
    public User(Integer id, String firstName, String lastName, LocalDate birthday, String email, Country country, List<Integer> friends) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.country = country;
        this.friends = friends;
    }

    /**
     * Constructor
     */
    public User() {

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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    /**
     * Test if user has a profile picture and return image extension
     * WARNING : Called in frontend
     * @return
     */
    public String getProfilePictureExt(){
        return new MSFileUtils().isFileExist(
                "src/main/resources/static/assets/profilePicture/" + this.getId() + "_" + this.getFirstName() + "_" + this.getLastName(),
                "img"
        );
    }

    /**
     * Cast name into "Fistname LASTNAME"
     * WARNING : Called in frontend
     * @return
     */
    public String getFullNameCasted(Boolean trunc) {
        if (trunc) return this.getFirstName().substring(0,1).toUpperCase() + "." + this.getLastName().toUpperCase();
        else return MSStringUtils.firstUpperCase(this.getFirstName()) + " " + this.getLastName().toUpperCase();
    }

    /**
     * Cast birthdate into "01/01/2000"
     * WARNING : Called in frontend
     * @return
     */
    public String getCastedBirthday(){
        String result = "";

        Integer dd = this.getBirthday().getDayOfMonth();
        Integer MM = this.getBirthday().getMonthValue();
        Integer YYYY = this.getBirthday().getYear();

        result += (dd < 10) ? "0" + dd : dd;
        result += "/";
        result += (MM < 10) ? "0" + MM : MM;
        result += "/";
        result += YYYY;

        return result;
    }

    /**
     * Return user age
     * WARNING : Called in frontend
     * @return
     */
    public Integer getAge(){
        if (this.getBirthday() != null) {
            return Period.between(this.getBirthday(), LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }
}
