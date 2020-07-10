package com.paymybuddy.PayMyBuddyWeb.models;

import com.paymybuddy.PayMyBuddyWeb.services.HTTPRequestService;
import org.json.JSONObject;

import java.io.IOException;

public class Currency {
    private String code;
    private Double ratebasedUSD;

    /**
     * Constructor
     * @param code
     * @throws IOException
     */
    public Currency(String code) throws IOException {
        this.code = code.toUpperCase();
        this.getInfoCountry(code.toUpperCase());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) throws IOException {
        this.code = code.toUpperCase();
        this.getInfoCountry(code.toUpperCase());
    }

    public Double getRatebasedUSD() {
        return ratebasedUSD;
    }

    public void setRatebasedUSD(Double ratebasedUSD) {
        this.ratebasedUSD = ratebasedUSD;
    }

    /**
     * Get Currency rate from External API
     * @param code
     * @throws IOException
     */
    private void getInfoCountry(String code) throws IOException {
        JSONObject data = new HTTPRequestService().getReq("https://api.exchangeratesapi.io/latest?base=USD", null);
        Integer status = data.getInt("status");
        if (status < 299) {
            JSONObject rates = (JSONObject) ((JSONObject) data.get("content")).get("rates");
            this.setRatebasedUSD(rates.getDouble(code));
        }
    }
}
