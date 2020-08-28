package com.paymybuddy.PayMyBuddyWeb.models;

import com.paymybuddy.PayMyBuddyWeb.interfaces.service.CountryServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.HTTPRequestServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.services.CountryService;
import com.paymybuddy.PayMyBuddyWeb.services.HTTPRequestService;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Country {
    private String code;
    private String wording;
    private final CountryServiceInterface countryService;

    /**
     * Constructor
     * @param code
     * @throws IOException
     */
    public Country(String code) throws IOException {
        this.countryService = new CountryService();
        this.setCountry(code);
    }

    public String getCode() {
        return code;
    }

    public String getWording() {
        return wording;
    }

    public void setCountry(String code) throws IOException {
        this.code = code.toUpperCase();
        this.wording = countryService.getNameOfCountry(code.toUpperCase());
    }
}
