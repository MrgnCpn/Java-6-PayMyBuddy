package com.paymybuddy.PayMyBuddyWeb.dao;

import com.paymybuddy.PayMyBuddyWeb.interfaces.DatabaseConfigurationInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.TransactionDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.models.Currency;
import com.paymybuddy.PayMyBuddyWeb.models.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class TransactionDAO implements TransactionDAOInterface {
    /**
     * Logger log4j2
     */
    private static final Logger logger = LogManager.getLogger("FriendsDAO");

    /**
     * Database configuration
     */
    private static DatabaseConfigurationInterface databaseConfiguration;

    /**
     * Constructor
     * @param databaseConfiguration
     */
    public TransactionDAO(DatabaseConfigurationInterface databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
    }

    /**
     * @see TransactionDAOInterface {@link #getUserTransactions(Integer)}
     */
    @Override
    public List<Transaction> getUserTransactions(Integer userId) {
        List<Transaction> result = new ArrayList<>();
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement ps = null;

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT from_isCard, from_id, to_id, date, description, amount, fee, final_amount, currency");
        sql.append(" FROM transactions");
        sql.append(" WHERE from_id = ? OR to_id = ?");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            rs = ps.executeQuery();
            while (rs.next()){
                Integer cardId = (rs.getBoolean("from_isCard")) ? rs.getInt("from_id") : null;
                Integer fromId = (rs.getBoolean("from_isCard")) ? null : rs.getInt("from_id");
                result.add(
                        new Transaction(
                                cardId,
                                fromId,
                                rs.getInt("to_id"),
                                rs.getDate("date").toLocalDate(),
                                rs.getString("description"),
                                rs.getDouble("amount"),
                                new Currency(rs.getString("currency"))
                        )
                );
            }
            logger.info("TransactionDAO.getUserTransactions() -> Transactions getted for user : " + userId);
        } catch (Exception e){
            logger.error("TransactionDAO.getUserTransactions() -> Error fetching user", e);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, rs);

        }
        return result;
    }

    /**
     * @see TransactionDAOInterface {@link #getTransactionsBetweenTwoUsers(Integer, Integer)}
     */
    @Override
    public List<Transaction> getTransactionsBetweenTwoUsers(Integer userId_1, Integer userId_2) {
        List<Transaction> result = new ArrayList<>();
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement ps = null;

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT date, description, amount, fee, final_amount, currency");
        sql.append(" FROM transactions");
        sql.append(" WHERE from_id = ? AND to_id = ? AND from_isCard = false");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, userId_1);
            ps.setInt(1, userId_2);
            rs = ps.executeQuery();
            while (rs.next()){
                result.add(
                        new Transaction(
                                null,
                                userId_1,
                                userId_2,
                                rs.getDate("date").toLocalDate(),
                                rs.getString("description"),
                                rs.getDouble("amount"),
                                new Currency(rs.getString("currency"))
                        )
                );
            }
            logger.info("TransactionDAO.getTransactionsBetweenTwoUsers() -> Transactions getted between " + userId_1 + " and " + userId_2);
        } catch (Exception e){
            logger.error("TransactionDAO.getTransactionsBetweenTwoUsers() -> Error fetching user", e);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, rs);

        }
        return result;
    }

    /**
     * @see TransactionDAOInterface {@link #newTransaction(Transaction)}
     */
    @Override
    public void newTransaction(Transaction transaction) {
        Connection con = null;
        PreparedStatement ps = null;

        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO transactions (from_isCard, from_id, to_id, date, description, amount, fee, final_amount, currency)");
        sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());

            if (transaction.getIdCard() == null) {
                ps.setBoolean(1, false);
                ps.setInt(2, transaction.getIdFrom());
            } else {
                ps.setBoolean(1, true);
                ps.setInt(2, transaction.getIdCard());
            }
            ps.setInt(3, transaction.getIdTo());
            ps.setDate(4, Date.valueOf(transaction.getDate()));
            ps.setString(5, transaction.getDescription());
            ps.setDouble(6, transaction.getAmount());
            ps.setDouble(7, transaction.getFee());
            ps.setDouble(8, transaction.getFinalAmount());
            ps.setString(9, transaction.getCurrency().getCode());
            ps.execute();
            logger.info("TransactionDAO.newTransaction() -> Transactions getted between " + transaction.getIdFrom() + " and " + transaction.getIdCard() + ", date : " + transaction.getDate());
        } catch (Exception ex){
            logger.error("TransactionDAO.newTransaction() -> Error update new user", ex);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, null);
        }
    }
}
