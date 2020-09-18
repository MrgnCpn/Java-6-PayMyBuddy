package com.paymybuddy.paymybuddyweb.configuration;

import com.paymybuddy.paymybuddyweb.interfaces.DatabaseConfigurationInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

@Singleton
public class DatabaseConfiguration implements DatabaseConfigurationInterface {

    /**
     * Logger log4j2
     */
    private static final Logger logger = LogManager.getLogger("DatabaseConfiguration");

    /**
     * Database host
     */
    private String host;

    /**
     * Database port
     */
    private String port;

    /**
     * Database database name
     */
    private String database;

    /**
     * Database username
     */
    private String user;

    /**
     * Database password
     */
    private String password;

    /**
     * Database configuration File
     */
    private String configurationFilePath;

    /**
     * Constructor
     * @param configurationFilePath
     */
    public DatabaseConfiguration(String configurationFilePath) {
        this.configurationFilePath = configurationFilePath;
    }

    /**
     * @see com.paymybuddy.paymybuddyweb.interfaces.DatabaseConfigurationInterface {@link #getConnection()}
     */
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (InputStream inputStream = new FileInputStream(configurationFilePath)){
            Properties properties = new Properties();
            properties.load(inputStream);
            host = properties.getProperty("host");
            port = properties.getProperty("port");
            database = properties.getProperty("database");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
        } catch (FileNotFoundException e) {
            logger.error("File not found", e);
        } catch (IOException e) {
            logger.error("Error while read file", e);
        }
        return DriverManager.getConnection(
                "jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
    }

    /**
     * @see com.paymybuddy.paymybuddyweb.interfaces.DatabaseConfigurationInterface {@link #closeConnection(Connection)}
     */
    @Override
    public void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                logger.error("Error while closing connection", e);
            }
        }
    }

    /**
     * @see com.paymybuddy.paymybuddyweb.interfaces.DatabaseConfigurationInterface {@link #closePreparedStatement(Statement)}
     */
    @Override
    public void closePreparedStatement(Statement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                logger.error("Error while closing prepared statement", e);
            }
        }
    }


    /**
     * @see com.paymybuddy.paymybuddyweb.interfaces.DatabaseConfigurationInterface {@link #closeResultSet(ResultSet)}
     */
    @Override
    public void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error("Error while closing result set", e);
            }
        }
    }

    /**
     * @see com.paymybuddy.paymybuddyweb.interfaces.DatabaseConfigurationInterface {@link #closeSQLTransaction(Connection, Statement, ResultSet)}
     */
    @Override
    public void closeSQLTransaction(Connection con, Statement ps, ResultSet rs){
        closeConnection(con);
        closePreparedStatement(ps);
        closeResultSet(rs);
    }
}
