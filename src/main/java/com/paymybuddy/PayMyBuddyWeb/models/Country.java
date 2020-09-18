package com.paymybuddy.paymybuddyweb.models;

import com.paymybuddy.paymybuddyweb.interfaces.service.CountryServiceInterface;
import com.paymybuddy.paymybuddyweb.services.CountryService;
import com.paymybuddy.paymybuddyweb.services.HTTPRequestService;

import java.io.IOException;

public class Country {
    private String code;
    private String wording;
    private CountryServiceInterface countryService;

    /**
     * Constructor
     * @param code
     * @throws IOException
     */
    public Country(String code) throws IOException {
        this.countryService = new CountryService(new HTTPRequestService());
        this.setCountry(code);
    }

    /**
     * Constructor
     * @param code
     * @param countryService
     * @throws IOException
     */
    public Country(String code, CountryServiceInterface countryService) throws IOException {
        this.countryService = countryService;
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
