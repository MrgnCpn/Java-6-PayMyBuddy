package com.paymybuddy.paymybuddyweb.dao;

import com.paymybuddy.paymybuddyweb.interfaces.DatabaseConfigurationInterface;
import com.paymybuddy.paymybuddyweb.interfaces.dao.SecurityDAOInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author MorganCpn
 */
@Singleton
public class SecurityDAO implements SecurityDAOInterface {
    /**
     * Logger log4j2
     */
    private static final Logger logger = LogManager.getLogger("UserDAO");

    /**
     * Database configuration
     */
    private DatabaseConfigurationInterface databaseConfiguration;

    /**
     * Constructor
     * @param databaseConfiguration
     */
    public SecurityDAO(DatabaseConfigurationInterface databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
    }

    /**
     * @see SecurityDAOInterface {@link #getUserPassword(String)}
     */
    @Override
    public String getUserPassword(String username) {
        String result = "";
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement ps = null;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT password FROM user WHERE email = ?");
        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setString(1, username);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getString("password");
            }
            logger.info("SecurityDAO.getUserPassword() -> Password get for user : {0}", username);
        } catch (Exception e){
            logger.error("SecurityDAO.getUserPassword() -> Error fetching user password", e);
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

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE user SET password = ? WHERE id = ?");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setString(1, userPassword);
            ps.setInt(2, userId);
            ps.execute();
            logger.info("SecurityDAO.updatePassword() -> Password updated for user : {0}", userId);
        } catch (Exception ex){
            logger.error("SecurityDAO.updatePassword() -> Error update user password", ex);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, null);
        }
    }
}
