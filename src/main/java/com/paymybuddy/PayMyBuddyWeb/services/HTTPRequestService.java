package com.paymybuddy.PayMyBuddyWeb.services;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HTTPRequestService {

    public HTTPRequestService() { }

    /**
     * API reader, HTTP GET request
     * @param url
     * @param params
     * @return Content of API GET request
     * @throws IOException
     */
    public JSONObject getReq(String url, Map<String, String> params) throws IOException {
        StringBuffer urlWithParams = new StringBuffer();
        urlWithParams.append(url).append(this.getURLParamsString(params));
        URL reqUrl = new URL(urlWithParams.toString());
        HttpURLConnection con = (HttpURLConnection) reqUrl.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        String res = this.getResponse(con);
        con.disconnect();
        return new JSONObject(res);
    }

    /**
     * Set URL parameters
     * @param params
     * @return URL parameters
     * @throws UnsupportedEncodingException
     */
    private static String getURLParamsString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        if (params != null && params.size() > 1) {
            result.append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                result.append("&");
            }
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

    /**
     * Parse request response into JSON
     * @param con
     * @return JSON
     * @throws IOException
     */
    private static String getResponse(HttpURLConnection con) throws IOException {
        StringBuffer res = new StringBuffer();
        res.append("{");
        res.append("\"status\" :").append("\"").append(con.getResponseCode()).append("\",");
        res.append("\"message\" :").append("\"").append(con.getResponseMessage()).append("\",");
        res.append("\"content\" :");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))){
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                res.append(inputLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        res.append("}");

        return res.toString();
    }
}
