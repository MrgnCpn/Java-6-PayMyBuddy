package com.paymybuddy.PayMyBuddyWeb.interfaces.service;

public interface SecurityServiceInterface {

    boolean isLogged();

    // private
    String encryptPassword();

    // private
    boolean decryptPassword();

    void changeUserPassword();

    void logUser();


}
