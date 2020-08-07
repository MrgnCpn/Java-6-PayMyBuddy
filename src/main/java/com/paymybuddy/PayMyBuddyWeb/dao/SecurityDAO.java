package com.paymybuddy.PayMyBuddyWeb.dao;

import com.paymybuddy.PayMyBuddyWeb.configuration.DatabaseConfiguration;
import com.paymybuddy.PayMyBuddyWeb.interfaces.DatabaseConfigurationInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.SecurityDAOInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class SecurityDAO implements SecurityDAOInterface {
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
    public SecurityDAO(DatabaseConfigurationInterface databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
    }

    /**
     * @see SecurityDAOInterface {@link #getUserPassword(Integer)}
     */
    @Override
    public String getUserPassword(Integer userId) {
        String result = "";
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement ps = null;

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append(" password");
        sql.append(" FROM");
        sql.append(" users");
        sql.append(" WHERE");
        sql.append(" userId = ?");
        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getString("password");
            }
        } catch (Exception e){
            logger.error("Error fetching user password", e);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, rs);
        }
        return result;
    }

    /**
     * @see SecurityDAOInterface {@link #updatePassword(Integer, String)}
     */
    @Override
    public void updatePassword(Integer userId, String userPassword) {
        Connection con = null;
        PreparedStatement ps = null;

        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE");
        sql.append(" users");
        sql.append(" SET");
        sql.append(" password = ?");
        sql.append(" id = ?");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setString(1, userPassword);
            ps.setInt(2, userId);
            ps.execute();
        } catch (Exception ex){
            logger.error("Error update user password", ex);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, null);
        }
    }
}
