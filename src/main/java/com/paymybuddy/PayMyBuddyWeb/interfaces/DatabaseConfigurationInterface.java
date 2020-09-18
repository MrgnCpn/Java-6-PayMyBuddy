package com.paymybuddy.paymybuddyweb.interfaces;

import java.sql.*;

public interface DatabaseConfigurationInterface {

    /**
     * Open Connection on OC_parkingSystem_p4_prod DB
     * @return Connection
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    Connection getConnection() throws ClassNotFoundException, SQLException;

    /**
     * Close Connection
     * @param con Active connection
     */
    void closeConnection(Connection con);

    /**
     * Close Prepared Statement
     * @param ps Open statement
     */
    void closePreparedStatement(Statement ps);

    /**
     * Close ResultSet
     * @param rs Open ResultSet
     */
    void closeResultSet(ResultSet rs);

    /**
     * Close all connection elements
     * @param con
     * @param ps
     * @param rs
     */
    void closeSQLTransaction(Connection con, Statement ps, ResultSet rs);
}
