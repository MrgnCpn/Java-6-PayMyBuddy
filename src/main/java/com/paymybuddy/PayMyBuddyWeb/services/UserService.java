package com.paymybuddy.PayMyBuddyWeb.services;

import com.paymybuddy.PayMyBuddyWeb.Utils.MSStringUtils;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.FriendDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.UserDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.SecurityServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.UserServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.models.Country;
import com.paymybuddy.PayMyBuddyWeb.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONValue;

import javax.inject.Singleton;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Singleton
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
     * Friends DAO
     */
    private final FriendDAOInterface friendDAO;

    /**
     * Constructor
     * @param securityService
     */
    public UserService(SecurityServiceInterface securityService, UserDAOInterface userDAO, FriendDAOInterface friendDAO) {
        this.securityService = securityService;
        this.userDAO = userDAO;
        this.friendDAO = friendDAO;
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
     * @see UserServiceInterface {@link #getUser(Integer)}
     */
    @Override
    public User getUser(Integer id) {
        User user = null;
        if (id != null && id > 0) {
            user = userDAO.getUserById(id);
        }
        return user;
    }

    /**
     * @see UserServiceInterface {@link #getUserFriends(User)}
     */
    @Override
    public List<User> getUserFriends(User user) {
        List<User> friendsList = null;
        if (user.getFriends() != null) {
            friendsList = new ArrayList<>();
            for (Integer id : user.getFriends() ) {
                friendsList.add(userDAO.getUserById(id));
            }
        }
        return friendsList;
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
            logger.error("UserService.updateUserProfile : Incomplete profile");
        }
    }

    /**
     * @see UserServiceInterface {@link #getIfAreFriends(HttpSession, Integer)}
     */
    @Override
    public Boolean getIfAreFriends(HttpSession session, Integer friendId) {
        Boolean result = false;
        Map <String, Object> userInfo = securityService.getUserInfoFromJWT(session);
        if (userInfo != null && friendId != null && friendId > 0) {
            result = friendDAO.areFriends((Integer) userInfo.get("userID"), friendId);
        }
        return result;
    }

    /**
     * @see UserServiceInterface {@link #removeFriend(HttpSession, Integer)}
     */
    @Override
    public void removeFriend(HttpSession session, Integer friendId) {
        Map <String, Object> userInfo = securityService.getUserInfoFromJWT(session);
        if (userInfo != null && friendId != null && friendId > 0) {
            friendDAO.removeFriendFromUser((Integer) userInfo.get("userID"), friendId);
        }
    }

    /**
     * @see UserServiceInterface {@link #addFriend(HttpSession, Integer)}
     */
    @Override
    public void addFriend(HttpSession session, Integer friendId) {
        Map <String, Object> userInfo = securityService.getUserInfoFromJWT(session);
        if (userInfo != null && friendId != null && friendId > 0) {
            friendDAO.addNewFriendToUser((Integer) userInfo.get("userID"), friendId);
        }
    }

    /**
     * @see UserServiceInterface {@link #searchContactUsers(HttpSession, String)}
     */
    @Override
    public String searchContactUsers(HttpSession session, String search) {
        StringBuffer data = new StringBuffer();
        Map <String, Object> userInfo = securityService.getUserInfoFromJWT(session);
        if (userInfo != null && !MSStringUtils.isEmpty(search)) {
            List<User> userList = userDAO.searchUsers((Integer) userInfo.get("userID"), search);
            if (userList != null) {
                data.append("[");
                for (User user : userList) {
                    data.append("{");
                    data.append("\"id\" : ").append(user.getId()).append(",");
                    data.append("\"firstName\" : \"").append(JSONValue.escape(user.getFirstName())).append("\",");
                    data.append("\"lastName\" : \"").append(JSONValue.escape(user.getLastName())).append("\",");
                    data.append("\"fullname\" : \"").append(JSONValue.escape(user.getFullNameCasted(false))).append("\",");
                    data.append("\"email\" : \"").append(JSONValue.escape(user.getEmail())).append("\",");
                    data.append("\"isFriend\" : ").append(this.getIfAreFriends(session, user.getId())).append(",");
                    String ext = user.getProfilePictureExt();
                    if (ext != null){
                        data.append("\"isProfilePicture\" : \"").append(ext).append("\"");
                    } else {
                        data.append("\"isProfilePicture\" : ").append(ext);
                    }


                    data.append("},");
                }
                if (data.charAt(data.length() - 1) == ',') data.delete(data.length() - 1, data.length());
                data.append("]");
                return data.toString();
            }
        }
        return null;
    }
}
