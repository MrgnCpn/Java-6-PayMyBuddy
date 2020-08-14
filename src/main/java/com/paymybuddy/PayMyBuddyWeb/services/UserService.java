package com.paymybuddy.PayMyBuddyWeb.services;

import com.paymybuddy.PayMyBuddyWeb.Utils.MSStringUtils;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.UserDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.SecurityServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.UserServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.models.Country;
import com.paymybuddy.PayMyBuddyWeb.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class UserService implements UserServiceInterface {
    /**
     * Logger log4j2
     */
    private static final Logger logger = LogManager.getLogger("UserService");

    /**
     * Security Service
     */
    private final SecurityServiceInterface securityService;

    /**
     * User DAO
     */
    private final UserDAOInterface userDAO;

    /**
     * Constructor
     * @param securityService
     */
    public UserService(SecurityServiceInterface securityService, UserDAOInterface userDAO) {
        this.securityService = securityService;
        this.userDAO = userDAO;
    }

    /**
     * @see UserServiceInterface {@link #getUser(HttpSession)}
     */
    @Override
    public User getUser(HttpSession session) {
        User user = null;
        Map <String, Object> userInfo = securityService.getUserInfoFromJWT(session);
        if (userInfo != null) {
            user = userDAO.getUserById((Integer) userInfo.get("userID"));
        }
        return user;
    }

    /**
     * @see UserServiceInterface {@link #updateUserProfile(HttpSession, Map)}
     */
    @Override
    public void updateUserProfile(HttpSession session, Map<String, Object> requestParams) throws IOException {
        Map <String, Object> userInfo = securityService.getUserInfoFromJWT(session);
        if (    userInfo != null &&
                requestParams != null &&
                !MSStringUtils.isEmpty((String) requestParams.get("firstname")) &&
                !MSStringUtils.isEmpty((String) requestParams.get("lastName")) &&
                !MSStringUtils.isEmpty((String) requestParams.get("email")) &&
                !MSStringUtils.isEmpty((String) requestParams.get("age_year")) &&
                !MSStringUtils.isEmpty((String) requestParams.get("age_month")) &&
                !MSStringUtils.isEmpty((String) requestParams.get("age_day")) &&
                !MSStringUtils.isEmpty((String) requestParams.get("country"))
        ) {
            LocalDate birthday = LocalDate.of(
                    Integer.valueOf((String) requestParams.get("age_year")),
                    Integer.valueOf((String) requestParams.get("age_month")),
                    Integer.valueOf((String) requestParams.get("age_day"))
            );
            Country country = new Country((String) requestParams.get("country"));
            User user = new User((Integer) userInfo.get("userID"), (String) requestParams.get("firstname"), (String) requestParams.get("lastname"), birthday, (String) requestParams.get("email"), country, null);
            userDAO.updateUser(user);
        } else {
            logger.info("UserService.updateUserProfile : Incomplete profile");
        }
    }
}
