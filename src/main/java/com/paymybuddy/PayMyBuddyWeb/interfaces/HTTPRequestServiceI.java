package com.paymybuddy.PayMyBuddyWeb.interfaces;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public interface HTTPRequestServiceI {
    /**
     * API reader, HTTP GET request
     * @param url
     * @param params
     * @return Content of API GET request
     * @throws IOException
     */
    JSONObject getReq(String url, Map<String, String> params) throws IOException;
}