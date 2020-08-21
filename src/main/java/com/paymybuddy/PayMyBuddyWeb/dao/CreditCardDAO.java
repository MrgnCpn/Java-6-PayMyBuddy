package com.paymybuddy.PayMyBuddyWeb.dao;

import com.paymybuddy.PayMyBuddyWeb.interfaces.DatabaseConfigurationInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.CreditCardDAOInterface;
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
        sql.append("SELECT id, card_type, card_number, card_cvv, card_date, wording");
        sql.append(" FROM credit_cards");
        sql.append(" WHERE user_id = ?");

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
                                rs.getString("card_date"),
                                rs.getString("wording")
                        )
                );
            }
            logger.info("CreditCardDAO.getUserCreditCards() -> cards get for user : " + userId);
        } catch (Exception e){
            logger.error("CreditCardDAO.getUserCreditCards() -> Error fetching user credit cards", e);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, rs);
        }
        return result;
    }

    /**
     * @see CreditCardDAOInterface {@link #getCardById(Integer, Integer)}
     */
    @Override
    public CreditCard getCardById(Integer cardId, Integer userId){
        CreditCard result = null;
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement ps = null;

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT card_type, card_number, card_cvv, card_date, wording");
        sql.append(" FROM credit_cards");
        sql.append(" WHERE id = ? AND user_id = ?");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, cardId);
            ps.setInt(2, userId);
            rs = ps.executeQuery();
            if (rs.next()){
                result = new CreditCard(
                    cardId,
                    userId,
                    rs.getString("card_type"),
                    rs.getString("card_number"),
                    rs.getString("card_cvv"),
                    rs.getString("card_date"),
                    rs.getString("wording")
                );
            }
            logger.info("CreditCardDAO.getCardById() -> card get by id : " + cardId);
        } catch (Exception e){
            logger.error("CreditCardDAO.getCardById() -> Error fetching credit card", e);
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
        sql.append("INSERT INTO credit_cards (user_id, card_type, card_number, card_cvv, card_date, wording)");
        sql.append(" VALUES (?, ?, ?, ?, ?, ?)");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, creditCard.getUserId());
            ps.setString(2, creditCard.getType());
            ps.setString(3, creditCard.getNumber());
            ps.setString(4, creditCard.getCvv());
            ps.setString(5, creditCard.getDate());
            ps.setString(6, creditCard.getWording());
            ps.execute();
            logger.info("CreditCardDAO.addCreditCard() -> Credit cards added for user : " + creditCard.getUserId());
        } catch (Exception ex){
            logger.error("CreditCardDAO.addCreditCard() -> Error add user card", ex);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, null);
        }
    }

    /**
     * @see CreditCardDAOInterface {@link #removeCreditCard(Integer, Integer)}
     */
    @Override
    public void removeCreditCard(Integer cardId, Integer userId){
        Connection con = null;
        PreparedStatement ps = null;

        StringBuffer sql = new StringBuffer();
        sql.append("DELETE FROM credit_cards");
        sql.append(" WHERE id = ? AND user_id = ?");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, cardId);
            ps.setInt(2, userId);
            ps.execute();
            logger.info("CreditCardDAO.removeCreditCard() -> Credit cards removed for user : " + userId);
        } catch (Exception ex){
            logger.error("CreditCardDAO.removeCreditCard() -> Error remove user card", ex);
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
        sql.append("UPDATE credit_cards");
        sql.append(" SET card_type = ?, card_number = ?, card_cvv = ?, card_date = ?, wording = ?");
        sql.append(" WHERE id = ? AND user_id = ?");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setString(1, creditCard.getType());
            ps.setString(2, creditCard.getNumber());
            ps.setString(3, creditCard.getCvv());
            ps.setString(4, creditCard.getDate());
            ps.setString(5, creditCard.getWording());
            ps.setInt(6, creditCard.getId());
            ps.setInt(7, creditCard.getUserId());
            ps.execute();
            logger.info("CreditCardDAO.updateCreditCard() -> Credit cards updated for user : " + creditCard.getUserId());
        } catch (Exception ex){
            logger.error("CreditCardDAO.updateCreditCard() -> Error update user card", ex);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, null);
        }
    }
}
