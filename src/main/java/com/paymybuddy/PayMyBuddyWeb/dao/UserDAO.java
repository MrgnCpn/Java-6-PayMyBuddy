package com.paymybuddy.PayMyBuddyWeb.dao;

import com.paymybuddy.PayMyBuddyWeb.interfaces.DatabaseConfigurationInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.AccountDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.CreditCardDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.FriendDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.UserDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.models.Country;
import com.paymybuddy.PayMyBuddyWeb.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Singleton
public class UserDAO implements UserDAOInterface {
    /**
     * Logger log4j2
     */
    private static final Logger logger = LogManager.getLogger("UserDAO");

    /**
     * Database configuration
     */
    private static DatabaseConfigurationInterface databaseConfiguration;

    private static CreditCardDAOInterface creditCardDAO;
    private static FriendDAOInterface friendDAO;
    private static AccountDAOInterface accountDAO;


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

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT firstname, lastname, birthday, email, country_code");
        sql.append(" FROM users");
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
        } catch (Exception e){
            logger.error("Error fetching user", e);
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

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT id, firstname, lastname, birthday, email, country_code");
        sql.append(" FROM users");
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
        } catch (Exception e){
            logger.error("Error fetching user", e);
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

        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE users SET firstname = ?, lastname = ?, birthday = ?, email = ?, country_code = ?");
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
        } catch (Exception ex){
            logger.error("Error update new user", ex);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, null);
        }
    }

    /**
     * @see UserDAOInterface {@link #createNewUser(User)}
     */
    @Override
    public void createNewUser(User user) {
        Connection con = null;
        PreparedStatement ps = null;

        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO users (firstname, lastname, birthday, email, country_code)");
        sql.append(" VALUES (?, ?, ?, ?, ?)");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setDate(3, Date.valueOf(user.getBirthday()));
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getCountry().getCode());
            ps.execute();
        } catch (Exception ex){
            logger.error("Error insert new user", ex);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, null);
        }
    }
}
