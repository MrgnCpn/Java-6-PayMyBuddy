package com.paymybuddy.PayMyBuddyWeb.dao;

import com.paymybuddy.PayMyBuddyWeb.interfaces.DatabaseConfigurationInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.CreditCardDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.TransactionDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.UserDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.models.CreditCard;
import com.paymybuddy.PayMyBuddyWeb.models.Currency;
import com.paymybuddy.PayMyBuddyWeb.models.Transaction;
import com.paymybuddy.PayMyBuddyWeb.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT DISTINCT");
        sql.append(" u_from.id,");
        sql.append(" u_from.firstname,");
        sql.append(" u_from.lastname,");
        sql.append(" u_from.email,");
        sql.append(" u_to.id,");
        sql.append(" u_to.firstname,");
        sql.append(" u_to.lastname,");
        sql.append(" u_to.email,");
        sql.append(" transactions.from_iscard,");
        sql.append(" transactions.from_id,");
        sql.append(" transactions.to_id,");
        sql.append(" transactions.date,");
        sql.append(" transactions.description,");
        sql.append(" transactions.amount,");
        sql.append(" transactions.currency");
        sql.append(" FROM transactions");
        sql.append(" INNER JOIN users u_from ON transactions.from_id = u_from.ID");
        sql.append(" INNER JOIN users u_to ON transactions.to_id = u_to.ID");
        sql.append(" WHERE from_id = ?");
        sql.append(" OR to_id = ?");
        sql.append(" ORDER BY transactions.date DESC");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            rs = ps.executeQuery();
            while (rs.next()){
                User user_from = null;
                CreditCard creditCard = null;

                if (!rs.getBoolean("transactions.from_iscard")) {
                    user_from = new User();
                    user_from.setId(rs.getInt("u_from.id"));
                    user_from.setFirstName(rs.getString("u_from.firstname"));
                    user_from.setLastName(rs.getString("u_from.lastname"));
                    user_from.setEmail(rs.getString("u_from.email"));
                } else {
                    creditCard = creditCardDAO.getCardById(rs.getInt("u_from.id"), userId);
                }

                User user_to = new User();
                user_to.setId(rs.getInt("u_to.id"));
                user_to.setFirstName(rs.getString("u_to.firstname"));
                user_to.setLastName(rs.getString("u_to.lastname"));
                user_to.setEmail(rs.getString("u_to.email"));

                result.add(
                    new Transaction(
                        creditCard,
                        user_from,
                        user_to,
                        rs.getDate("date").toLocalDate(),
                        rs.getString("description"),
                        rs.getDouble("amount"),
                        new Currency(rs.getString("currency"))
                    )
                );
            }
            logger.info("TransactionDAO.getUserTransactions() -> Transactions get for user : " + userId);
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

        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO transactions (from_isCard, from_id, to_id, date, description, amount, fee, final_amount, currency)");
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
            con.rollback();
        } finally {
            con.setAutoCommit(true);
            databaseConfiguration.closeSQLTransaction(con, ps, null);
        }
    }
}
