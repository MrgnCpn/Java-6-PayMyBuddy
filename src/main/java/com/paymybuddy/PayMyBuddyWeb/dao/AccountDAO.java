package com.paymybuddy.PayMyBuddyWeb.dao;

import com.paymybuddy.PayMyBuddyWeb.configuration.DatabaseConfiguration;
import com.paymybuddy.PayMyBuddyWeb.interfaces.DatabaseConfigurationInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.AccountDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.models.Account;
import com.paymybuddy.PayMyBuddyWeb.models.Currency;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Singleton
public class AccountDAO implements AccountDAOInterface {
    /**
     * Logger log4j2
     */
    private static final Logger logger = LogManager.getLogger("UserDAO");

    /**
     * Database configuration
     */
    private static DatabaseConfigurationInterface databaseConfiguration;

    /**
     * Constructor
     * @param databaseConfiguration
     */
    public AccountDAO(DatabaseConfigurationInterface databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
    }

    /**
     * @see AccountDAOInterface {@link #getAccount(Integer)}
     */
    @Override
    public Account getAccount(Integer userId){
        Account result = null;
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement ps = null;

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT amount, currency, balance_date");
        sql.append(" FROM accounts");
        sql.append(" WHERE user_id = ?");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if(rs.next()){
                result = new Account(
                        userId,
                        rs.getDouble("amount"),
                        new Currency(rs.getString("currency")),
                        rs.getDate("balance_date").toLocalDate());
            }
        } catch (Exception e){
            logger.error("Error fetching user account", e);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, rs);

        }
        return result;
    }

    /**
     * @see AccountDAOInterface {@link #updateAccount(Account)}
     */
    @Override
    public void updateAccount(Account account){
        Connection con = null;
        PreparedStatement ps = null;

        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE accounts");
        sql.append(" SET amount = ?, currency = ?, balance_date = ?");
        sql.append(" WHERE user_id = ?");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setDouble(1, account.getAmount());
            ps.setString(2, account.getCurrency().getCode());
            ps.setDate(3, Date.valueOf(account.getDate()));
            ps.setInt(4, account.getUserId());
            ps.execute();
        } catch (Exception ex){
            logger.error("Error update user account", ex);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, null);
        }
    }

    /**
     * @see AccountDAOInterface {@link #updateAccount(Account)}
     */
    @Override
    public void createAccount(Integer userId, String currency){
        Connection con = null;
        PreparedStatement ps = null;

        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO accounts (user_id, amount, currency, balance_date)");
        sql.append(" VALUES (?, 0, ?, NOW())");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, userId);
            ps.setString(2, currency);
            ps.execute();
        } catch (Exception ex){
            logger.error("Error create user account", ex);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, null);
        }
    }
}
