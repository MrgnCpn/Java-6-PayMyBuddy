package com.paymybuddy.paymybuddyweb.dao;

import com.paymybuddy.paymybuddyweb.interfaces.DatabaseConfigurationInterface;
import com.paymybuddy.paymybuddyweb.interfaces.dao.CreditCardDAOInterface;
import com.paymybuddy.paymybuddyweb.interfaces.dao.TransactionDAOInterface;
import com.paymybuddy.paymybuddyweb.models.CreditCard;
import com.paymybuddy.paymybuddyweb.models.Currency;
import com.paymybuddy.paymybuddyweb.models.Transaction;
import com.paymybuddy.paymybuddyweb.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MorganCpn
 */
@Singleton
public class TransactionDAO implements TransactionDAOInterface {
    /**
     * Logger log4j2
     */
    private static final Logger logger = LogManager.getLogger("TransactionDAO");

    /**
     * Database configuration
     */
    private DatabaseConfigurationInterface databaseConfiguration;

    /**
     * Credit card DAO
     */
    private CreditCardDAOInterface creditCardDAO;

    /**
     * Constructor
     * @param databaseConfiguration
     * @param creditCardDAO
     */
    public TransactionDAO(DatabaseConfigurationInterface databaseConfiguration, CreditCardDAOInterface creditCardDAO) {
        this.databaseConfiguration = databaseConfiguration;
        this.creditCardDAO = creditCardDAO;
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

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT");
        sql.append(" u_from.id,");
        sql.append(" u_from.firstname,");
        sql.append(" u_from.lastname,");
        sql.append(" u_from.email,");
        sql.append(" u_to.id,");
        sql.append(" u_to.firstname,");
        sql.append(" u_to.lastname,");
        sql.append(" u_to.email,");
        sql.append(" transaction.from_iscard,");
        sql.append(" transaction.from_userId,");
        sql.append(" transaction.to_userId,");
        sql.append(" transaction.date,");
        sql.append(" transaction.description,");
        sql.append(" transaction.amount,");
        sql.append(" transaction.currency");
        sql.append(" FROM transaction");
        sql.append(" INNER JOIN user u_from ON transaction.from_userId = u_from.ID");
        sql.append(" INNER JOIN user u_to ON transaction.to_userId = u_to.ID");
        sql.append(" WHERE from_userId = ?");
        sql.append(" OR to_userId = ?");
        sql.append(" ORDER BY transaction.date DESC");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            rs = ps.executeQuery();
            while (rs.next()){
                User userFrom = null;
                CreditCard creditCard = null;

                if (!rs.getBoolean("transaction.from_iscard")) {
                    userFrom = new User();
                    userFrom.setId(rs.getInt("u_from.id"));
                    userFrom.setFirstName(rs.getString("u_from.firstname"));
                    userFrom.setLastName(rs.getString("u_from.lastname"));
                    userFrom.setEmail(rs.getString("u_from.email"));
                } else {
                    creditCard = creditCardDAO.getCardById(rs.getInt("u_from.id"), userId);
                }

                User userTo = new User();
                userTo.setId(rs.getInt("u_to.id"));
                userTo.setFirstName(rs.getString("u_to.firstname"));
                userTo.setLastName(rs.getString("u_to.lastname"));
                userTo.setEmail(rs.getString("u_to.email"));

                result.add(
                    new Transaction(
                        creditCard,
                        userFrom,
                        userTo,
                        rs.getDate("date").toLocalDate(),
                        rs.getString("description"),
                        rs.getDouble("amount"),
                        new Currency(rs.getString("currency"))
                    )
                );
            }
            logger.info("TransactionDAO.getUserTransactions() -> Transactions get for user : {0}", userId);
        } catch (Exception e){
            logger.error("TransactionDAO.getUserTransactions() -> Error fetching user", e);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, rs);

        }
        return result;
    }

    /**
     * @see TransactionDAOInterface {@link #newTransaction(Transaction)}
     */
    @Override
    public void newTransaction(Transaction transaction) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO transaction (from_isCard, from_userId, to_userId, date, description, amount, fee, final_amount, currency)");
        sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        try {
            con = databaseConfiguration.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql.toString());

            if (transaction.getCard() == null) {
                ps.setBoolean(1, false);
                ps.setInt(2, transaction.getUserFrom().getId());
            } else {
                ps.setBoolean(1, true);
                ps.setInt(2, transaction.getCard().getId());
            }
            ps.setInt(3, transaction.getUserTo().getId());
            ps.setDate(4, Date.valueOf(transaction.getDate()));
            ps.setString(5, transaction.getDescription());
            ps.setDouble(6, transaction.getAmount());
            ps.setDouble(7, transaction.getFee());
            ps.setDouble(8, transaction.getFinalAmount());
            ps.setString(9, transaction.getCurrency().getCode());
            ps.execute();
            logger.info("TransactionDAO.newTransaction() -> Transactions created");
            con.commit();
        } catch (Exception ex){
            logger.error("TransactionDAO.newTransaction() -> Error new transaction", ex);
            if (con != null) con.rollback();
        } finally {
            if (con != null) con.setAutoCommit(true);
            databaseConfiguration.closeSQLTransaction(con, ps, null);
        }
    }
}
