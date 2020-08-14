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
    private final CountryServiceInterface countryServiceInterface = new CountryService();

    /**
     * Constructor
     * @param code
     * @throws IOException
     */
    public Country(String code) throws IOException {
        this.code = code.toUpperCase();
        this.wording = countryServiceInterface.getNameOfCountry(code.toUpperCase());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) throws IOException {
        this.code = code.toUpperCase();
        this.wording = countryServiceInterface.getNameOfCountry(code.toUpperCase());
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String code) throws IOException {
        this.wording = countryServiceInterface.getNameOfCountry(code.toUpperCase());
    }
}
