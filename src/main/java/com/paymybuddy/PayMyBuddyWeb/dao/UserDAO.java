package com.paymybuddy.paymybuddyweb.dao;

import com.paymybuddy.paymybuddyweb.interfaces.DatabaseConfigurationInterface;
import com.paymybuddy.paymybuddyweb.interfaces.dao.AccountDAOInterface;
import com.paymybuddy.paymybuddyweb.interfaces.dao.CreditCardDAOInterface;
import com.paymybuddy.paymybuddyweb.interfaces.dao.FriendDAOInterface;
import com.paymybuddy.paymybuddyweb.interfaces.dao.UserDAOInterface;
import com.paymybuddy.paymybuddyweb.models.Country;
import com.paymybuddy.paymybuddyweb.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MorganCpn
 */
@Singleton
public class UserDAO implements UserDAOInterface {
    /**
     * Logger log4j2
     */
    private static final Logger logger = LogManager.getLogger("UserDAO");

    /**
     * Database configuration
     */
    private DatabaseConfigurationInterface databaseConfiguration;

    private CreditCardDAOInterface creditCardDAO;
    private FriendDAOInterface friendDAO;
    private AccountDAOInterface accountDAO;


    /**
     * Constructor
     * @param databaseConfiguration
     * @param creditCardDAO
     * @param friendDAO
     * @param accountDAO
     */
    public UserDAO(DatabaseConfigurationInterface databaseConfiguration, CreditCardDAOInterface creditCardDAO, FriendDAOInterface friendDAO, AccountDAOInterface accountDAO) {
        this.databaseConfiguration = databaseConfiguration;
        this.creditCardDAO = creditCardDAO;
        this.friendDAO = friendDAO;
        this.accountDAO = accountDAO;
    }

    /**
     * @see UserDAOInterface {@link #getUserById(Integer)}
     */
    @Override
    public User getUserById(Integer userId) {
        User result = null;
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement ps = null;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT firstname, lastname, birthday, email, country_code");
        sql.append(" FROM user");
        sql.append(" WHERE id = ?");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if(rs.next()){
                result = new User();
                result.setId(userId);
                result.setFirstName(rs.getString("firstname"));
                result.setLastName(rs.getString("lastname"));
                result.setBirthday(rs.getDate("birthday").toLocalDate());
                result.setEmail(rs.getString("email"));
                result.setCountry(new Country(rs.getString("country_code")));
                result.setFriends(friendDAO.getUserFriends(userId));
                result.setAccount(accountDAO.getAccount(userId));
                result.setCreditCards(creditCardDAO.getUserCreditCards(userId));
            }
            logger.info("UserDAO.getUserById() -> Profile get for user : {0}", userId);
        } catch (Exception e){
            logger.error("UserDAO.getUserById() -> Error fetching user", e);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, rs);
        }
        return result;
    }

    @Override
    public User getUserByUsername(String username) {
        User result = null;
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement ps = null;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, firstname, lastname, birthday, email, country_code");
        sql.append(" FROM user");
        sql.append(" WHERE email = ?");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setString(1, username);
            rs = ps.executeQuery();
            if(rs.next()){
                result = new User();
                result.setId(rs.getInt("id"));
                result.setFirstName(rs.getString("firstname"));
                result.setLastName(rs.getString("lastname"));
                result.setBirthday(rs.getDate("birthday").toLocalDate());
                result.setEmail(rs.getString("email"));
                result.setCountry(new Country(rs.getString("country_code")));
                result.setFriends(friendDAO.getUserFriends(rs.getInt("id")));
                result.setAccount(accountDAO.getAccount(rs.getInt("id")));
                result.setCreditCards(creditCardDAO.getUserCreditCards(rs.getInt("id")));
            }
            logger.info("UserDAO.getUserByUsername() -> Profile get for user : {0}", username);
        } catch (Exception e){
            logger.error("UserDAO.getUserByUsername() -> Error fetching user", e);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, rs);
        }
        return result;
    }

    /**
     * @see UserDAOInterface {@link #updateUser(User)}
     */
    @Override
    public void updateUser(User user) {
        Connection con = null;
        PreparedStatement ps = null;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE user SET firstname = ?, lastname = ?, birthday = ?, email = ?, country_code = ?");
        sql.append(" WHERE id = ?");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setDate(3, Date.valueOf(user.getBirthday()));
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getCountry().getCode());
            ps.setInt(6, user.getId());
            ps.execute();
            logger.info("UserDAO.updateUser() -> Profile updated for user : {0}", user.getId());
        } catch (Exception ex){
            logger.error("UserDAO.updateUser() -> Error update new user", ex);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, null);
        }
    }

    /**
     * @see UserDAOInterface {@link #createUser(User, String, String)}
     */
    @Override
    public void createUser(User user, String currency, String password) {
        Connection con = null;
        PreparedStatement ps = null;

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO user (firstname, lastname, birthday, email, password, country_code)");
        sql.append(" VALUES (?, ?, ?, ?, ?, ?)");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setDate(3, Date.valueOf(user.getBirthday()));
            ps.setString(4, user.getEmail());
            ps.setString(5, password);
            ps.setString(6, user.getCountry().getCode());
            ps.execute();

            Integer userID = getUserByUsername(user.getEmail()).getId();
            logger.info("UserDAO.createUser() -> Profile created for user : {0}", userID);
            accountDAO.createAccount(userID, currency);
        } catch (Exception ex){
            logger.error("UserDAO.createUser() -> Error create new user", ex);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, null);
        }
    }

    /**
     * @see UserDAOInterface {@link #searchUsers(Integer, String)}
     */
    @Override
    public List<User> searchUsers(Integer userID, String search) {
        List<User> result = null;
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement ps = null;

        search += "%";

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, firstname, lastname, email");
        sql.append(" FROM user");
        sql.append(" WHERE (firstname LIKE(?)");
        sql.append(" OR lastname LIKE (?)");
        sql.append(" OR email LIKE(?))");
        sql.append(" AND id <> ?");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);
            ps.setInt(4, userID);
            rs = ps.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                result.add(user);
            }
            if(result.size() <= 0) {
                result = null;
            }
        } catch (Exception e){
            logger.error("UserDAO.searchUser() -> Error fetching users", e);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, rs);
        }
        return result;
    }
}
