package com.paymybuddy.PayMyBuddyWeb.interfaces.service;

import com.paymybuddy.PayMyBuddyWeb.models.User;
import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface SecurityServiceInterface {

    /**
     * Encode user password and insert into db
     * @param userId
     * @param password
     */
    void registerUser(Integer userId, String password);

    /**
     * Check user log and return login information with token
     * @param username
     * @param password
     * @return
     */
    Map<String, String> logUser(String username, String password);

    /**
     * Create JWT
     * @param id
     * @param subject
     * @param issuer
     * @param claims
     * @param ttlMillis
     * @return
     */
    String createJWT(Integer id, String subject, String issuer, Map<String, Object> claims, long ttlMillis);

    /**
     * Parse JWT
     * @param token
     * @return
     */
    Claims parseJWT(String token);

    /**
     * Check if user is logged
     * @param session
     * @return
     */
    Boolean isLog(HttpSession session);
}
