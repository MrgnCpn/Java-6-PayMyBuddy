package com.paymybuddy.PayMyBuddyWeb.services;

import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.SecurityDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.UserDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.SecurityServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class SecurityService implements SecurityServiceInterface {
    /**
     * Logger log4j2
     */
    private static final Logger logger = LogManager.getLogger("SecurityService");

    /**
     * Security DAO
     */
    private SecurityDAOInterface securityDAO;

    /**
     * User DAO
     */
    private UserDAOInterface userDAO;

    /**
     * Password manager
     */
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Constructor
     * @param securityDAO
     */
    public SecurityService(SecurityDAOInterface securityDAO, BCryptPasswordEncoder passwordEncoder, UserDAOInterface userDAO) {
        this.securityDAO = securityDAO;
        this.passwordEncoder = passwordEncoder;
        this.userDAO = userDAO;
    }

    /**
     * @see SecurityDAOInterface {@link #registerUser(Integer, String)}
     */
    @Override
    public void registerUser(Integer userId, String password) {
        securityDAO.updatePassword(userId, passwordEncoder.encode(password));
    }

    /**
     * @see SecurityDAOInterface {@link #logUser(String, String)}
     */
    @Override
    public Map<String, String> logUser(String username, String password) {
        Map<String, String> loginInformations = null;
        String encodePassword = securityDAO.getUserPassword(username);
        if (passwordEncoder.matches(password, encodePassword)) {
            loginInformations = new HashMap<>();
            User user = userDAO.getUserByUsername(username);
            loginInformations.put("id", user.getId().toString());
            loginInformations.put("username", user.getEmail());
        }
        return loginInformations;
    }
}
