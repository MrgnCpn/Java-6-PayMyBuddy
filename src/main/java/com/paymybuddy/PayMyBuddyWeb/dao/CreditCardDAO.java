package com.paymybuddy.PayMyBuddyWeb.dao;

import com.paymybuddy.PayMyBuddyWeb.configuration.DatabaseConfiguration;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.CreditCardDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.DatabaseConfigurationInterface;
import com.paymybuddy.PayMyBuddyWeb.models.CreditCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class CreditCardDAO implements CreditCardDAOInterface {
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
    public CreditCardDAO(DatabaseConfigurationInterface databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
    }

    /**
     * @see CreditCardDAOInterface {@link #getUserCreditCards(Integer)}
     */
    @Override
    public List<CreditCard> getUserCreditCards(Integer userId){
        List<CreditCard> result = new ArrayList<>();
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement ps = null;

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append(" id, card_type, card_number, card_cvv, wording");
        sql.append(" FROM");
        sql.append(" CREDIT_CARDS");
        sql.append(" WHERE");
        sql.append(" user_id = ?");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()){
                result.add(
                        new CreditCard(
                                rs.getInt("id"),
                                userId,
                                rs.getString("card_type"),
                                rs.getString("card_number"),
                                rs.getString("card_cvv"),
                                rs.getString("wording")
                        )
                );
            }
        } catch (Exception e){
            logger.error("Error fetching user credit cards", e);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, rs);

        }
        return result;
    }

    /**
     * @see CreditCardDAOInterface {@link #addCreditCard(CreditCard)}
     */
    @Override
    public void addCreditCard(CreditCard creditCard){
        Connection con = null;
        PreparedStatement ps = null;

        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO");
        sql.append(" credit_cards (user_id, card_type, card_number, card_cvv, wording)");
        sql.append(" VALUES");
        sql.append(" (?, ?, ?, ?, ?)");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, creditCard.getUserId());
            ps.setString(2, creditCard.getType());
            ps.setString(3, creditCard.getNumber());
            ps.setString(4, creditCard.getCvv());
            ps.setString(5, creditCard.getWording());
            ps.execute();
        } catch (Exception ex){
            logger.error("Error add user card", ex);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, null);
        }
    }

    /**
     * @see CreditCardDAOInterface {@link #removeCreditCard(CreditCard)}
     */
    @Override
    public void removeCreditCard(CreditCard creditCard){
        Connection con = null;
        PreparedStatement ps = null;

        StringBuffer sql = new StringBuffer();
        sql.append("DELETE");
        sql.append(" credit_cards");
        sql.append(" WHERE");
        sql.append(" id = ?");
        sql.append(" AND");
        sql.append(" user_id = ?");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, creditCard.getCardId());
            ps.setInt(2, creditCard.getUserId());
            ps.execute();
        } catch (Exception ex){
            logger.error("Error remove user card", ex);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, null);
        }
    }

    /**
     * @see CreditCardDAOInterface {@link #updateCreditCard(CreditCard)}
     */
    @Override
    public void updateCreditCard(CreditCard creditCard){
        Connection con = null;
        PreparedStatement ps = null;

        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE");
        sql.append(" credit_cards");
        sql.append(" SET");
        sql.append(" card_type = ?,");
        sql.append(" card_number = ?,");
        sql.append(" card_cvv = ?,");
        sql.append(" wording = ?");
        sql.append(" WHERE");
        sql.append(" id = ?");
        sql.append(" AND");
        sql.append(" user_id = ?");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setString(1, creditCard.getType());
            ps.setString(2, creditCard.getNumber());
            ps.setString(3, creditCard.getCvv());
            ps.setString(4, creditCard.getWording());
            ps.setInt(5, creditCard.getCardId());
            ps.setInt(6, creditCard.getUserId());
            ps.execute();
        } catch (Exception ex){
            logger.error("Error update user card", ex);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, null);
        }
    }
}
