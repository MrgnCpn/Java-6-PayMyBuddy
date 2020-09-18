package com.paymybuddy.paymybuddyweb.interfaces.service;

import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public interface SecurityServiceInterface {

    /**
     * Encode user password and insert into db
     * @param signUpParams
     */
    Map<String, String> registerUser(Map<String, Object> signUpParams) throws IOException;

    /**
     * Check user log and return login information with token
     * @param username
     * @param password
     * @param rememeberUser
     * @return
     */
    Map<String, String> logUser(String username, String password, Boolean rememeberUser);

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
    boolean isLog(HttpSession session);

    /**
     * Update user password
     * @param session
     * @param requestParams
     */
    void updateUserPassword(HttpSession session, Map<String, Object> requestParams);

    /**
     * Parse JWT token
     * @param session
     * @return
     */
    Map<String, Object> getUserInfoFromJWT(HttpSession session);
}
