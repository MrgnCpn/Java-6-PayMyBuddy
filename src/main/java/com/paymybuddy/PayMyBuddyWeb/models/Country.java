package com.paymybuddy.PayMyBuddyWeb.models;

import com.paymybuddy.PayMyBuddyWeb.services.HTTPRequestService;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Country {
    private String code;
    private String wording;

    /**
     * Constructor
     * @param code
     * @throws IOException
     */
    public Country(String code) throws IOException {
        this.code = code.toUpperCase();
        this.getInfo(code.toUpperCase());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) throws IOException {
        this.code = code.toUpperCase();
        this.getInfo(code.toUpperCase());
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    /**
     * Get Country info from External API
     * @param code
     * @throws IOException
     */
    private void getInfo(String code) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("fields", "name");
        JSONObject data = new HTTPRequestService().getReq("https://restcountries.eu/rest/v2/alpha/" + code, params);
        Integer status = data.getInt("status");
        if (status < 299) {
            JSONObject content = (JSONObject) data.get("content");
            this.setWording(content.getString("name"));
        }
    }
}
