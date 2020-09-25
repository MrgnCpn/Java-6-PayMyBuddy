package com.paymybuddy.paymybuddyweb;

import com.paymybuddy.paymybuddyweb.configuration.DatabaseConfiguration;
import com.paymybuddy.paymybuddyweb.interfaces.DatabaseConfigurationInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseTestDAO {
    /**
     * Logger log4j2
     */
    private static final Logger logger = LogManager.getLogger("DatabaseConfiguration_Test");

    /**
     * DatabaseConfiguration
     */
    private DatabaseConfigurationInterface databaseConfiguration;

    /**
     * Constructor
     */
    public DatabaseTestDAO() {
        this.databaseConfiguration = new DatabaseConfiguration("src/main/resources/static/database/databaseConfiguration_test.properties");
    }

    /**
     * Getter DatabaseConfiguration
     * @return
     */
    public DatabaseConfigurationInterface getDatabaseConfiguration() {
        return databaseConfiguration;
    }

    /**
     * Reset Test Database
     * @throws SQLException
     */
    public void resetDatabase() throws SQLException {
        Connection con = null;
        StringBuilder sql;

        try {
            con = databaseConfiguration.getConnection();
            con.setAutoCommit(false);

            con.prepareStatement("SET FOREIGN_KEY_CHECKS = 0;").execute();

            con.prepareStatement("TRUNCATE TABLE ACCOUNT").execute();
            con.prepareStatement("TRUNCATE TABLE TRANSACTION").execute();
            con.prepareStatement("TRUNCATE TABLE CREDIT_CARD").execute();
            con.prepareStatement("TRUNCATE TABLE FRIEND").execute();
            con.prepareStatement("TRUNCATE TABLE USER").execute();

            con.prepareStatement("SET FOREIGN_KEY_CHECKS = 1;").execute();

            sql = new StringBuilder();
            sql.append("INSERT INTO USER(firstname, lastname, birthday, email, password, country_code)");
            sql.append(" VALUES");
            sql.append(" ('juanita', 'emard', '1995-01-06', 'juanita.emard@email.com', '$2a$10$w4YKpuluFDrfomqaBzy1w./GTA57TtnAX6PngUtMhpk6KUGQBbe2e', 'FRA'),");
            sql.append(" ('alexane', 'collins', '1989-11-22', 'alexane.collins@email.com', '$2a$10$Qp96gr3vEtUHlFFAnfPGVOnnaTgPOgKDpbo5f1X76UKteqprzmQma', 'GBR'),");
            sql.append(" ('ford', 'bashirian', '1997-09-13', 'ford.bashirian@email.com', '$2a$10$KOAKk39lx.QsbuXTeuDPzOdDYZbS4k50HMH/KQJPlttLKlf0w4pO.', 'USA');");

            con.prepareStatement(sql.toString()).execute();

            sql.delete(0, sql.length());
            sql.append("INSERT INTO TRANSACTION (from_isCard, from_userId, to_userId, date, description, amount, fee, final_amount, currency)");
            sql.append(" VALUES");
            sql.append(" (true, 1, 1, '2019-1-25 17:0:38', 'Feed Account', 1000.00, 5.00, 995.00, 'USD'),");
            sql.append(" (false, 1, 2, '2019-6-7 16:58:25', 'Eu consequat ac felis donec et odio pellentesque.', 100.00, 0.50, 99.50, 'USD'),");
            sql.append(" (true, 2, 2, '2019-1-25 17:0:38', 'Feed Account', 100.00, 0.50, 99.50, 'USD');");

            con.prepareStatement(sql.toString()).execute();

            sql.delete(0, sql.length());
            sql.append("INSERT INTO CREDIT_CARD (user_id, card_type, card_number, card_cvv, card_date, wording) VALUES");
            sql.append(" (1, 'MAST', '1447560945069489', '565', '01/20', 'My Card - Boursorama'),");
            sql.append(" (1, 'VISA', '2049648612159233', '079', '01/20', 'My Card - HSBC'),");
            sql.append(" (2, 'MAST', '4251056551139390', '071', '01/20', 'My Card - Bank of America');");

            con.prepareStatement(sql.toString()).execute();

            sql.delete(0, sql.length());
            sql.append("INSERT INTO ACCOUNT (user_id, amount, currency, balance_date)");
            sql.append(" VALUES");
            sql.append(" (1, 895.00, 'USD', NOW()),");
            sql.append(" (2, 199.50, 'USD', NOW());");

            con.prepareStatement(sql.toString()).execute();

            sql.delete(0, sql.length());
            sql.append("INSERT INTO  FRIEND (user_id, friend_id)");
            sql.append(" VALUES (1, 2), (2, 1);");

            con.prepareStatement(sql.toString()).execute();

            con.commit();
            logger.info("DatabaseTestDAO.resetTestDatabase() -> Reset Test Database");
        } catch (Exception ex){
            logger.error("DatabaseTestDAO.resetTestDatabase() -> Error reset", ex);
            con.rollback();
        } finally {
            if(con != null) con.setAutoCommit(true);
            databaseConfiguration.closeSQLTransaction(con, null, null);
        }
    }
}
