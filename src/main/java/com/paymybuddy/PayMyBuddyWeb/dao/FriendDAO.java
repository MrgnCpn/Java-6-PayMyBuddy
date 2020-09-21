package com.paymybuddy.paymybuddyweb.dao;

import com.paymybuddy.paymybuddyweb.interfaces.DatabaseConfigurationInterface;
import com.paymybuddy.paymybuddyweb.interfaces.dao.FriendDAOInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MorganCpn
 */
@Singleton
public class FriendDAO implements FriendDAOInterface {
    /**
     * Logger log4j2
     */
    private static final Logger logger = LogManager.getLogger("FriendsDAO");

    /**
     * Database configuration
     */
    private DatabaseConfigurationInterface databaseConfiguration;

    /**
     * Constructor
     * @param databaseConfiguration
     */
    public FriendDAO(DatabaseConfigurationInterface databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
    }

    /**
     * @see FriendDAOInterface {@link #getUserFriends(Integer)}
     */
    @Override
    public List<Integer> getUserFriends(Integer userId) {
        List<Integer> result = null;
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement ps = null;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT friend_id FROM friends WHERE user_id = ?");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                result.add(rs.getInt("friend_id"));
            }
            if (result.size() > 0) {
                logger.info("FriendDAO.getUserFriends() -> Friends get for user : {0}", userId);
            } else {
                result = null;
                logger.info("FriendDAO.getUserFriends() -> No friends for user : {0}", userId);
            }
        } catch (Exception e){
            logger.error("FriendDAO.getUserFriends() -> Error fetching friends", e);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, rs);
        }
        return result;
    }

    /**
     * @see FriendDAOInterface {@link #addNewFriendToUser(Integer, Integer)}
     */
    @Override
    public void addNewFriendToUser(Integer userId, Integer friendId){
        Connection con = null;
        PreparedStatement ps = null;

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO friends (user_id, friend_id)");
        sql.append(" VALUES (?, ?)");

        try {
            con = databaseConfiguration.getConnection();

            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, userId);
            ps.setInt(2, friendId);
            ps.execute();

            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, friendId);
            ps.setInt(2, userId);
            ps.execute();

            logger.info("FriendDAO.addNewFriendToUser() -> Friendship added between {0} and {1}", userId, friendId);
        } catch (Exception ex){
            logger.error("FriendDAO.addNewFriendToUser() -> Error add user friend", ex);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, null);
        }
    }

    /**
     * @see FriendDAOInterface {@link #removeFriendFromUser(Integer, Integer)}
     */
    @Override
    public void removeFriendFromUser(Integer userId, Integer friendId){
        Connection con = null;
        PreparedStatement ps = null;

        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM friends");
        sql.append(" WHERE user_id = ? AND friend_id = ?");

        try {
            con = databaseConfiguration.getConnection();

            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, userId);
            ps.setInt(2, friendId);
            ps.execute();

            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, friendId);
            ps.setInt(2, userId);
            ps.execute();

            logger.info("FriendDAO.removeFriendFromUser() -> Friendship removed between {0} and {1}", userId, friendId);
        } catch (Exception ex){
            logger.error("FriendDAO.removeFriendFromUser() -> Error remove user friend", ex);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, null);
        }
    }

    /**
     * @see FriendDAOInterface {@link #areFriends(Integer, Integer)}
     */
    @Override
    public Boolean areFriends(Integer userId, Integer friendId) {
        Boolean result = false;
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement ps = null;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT friend_id FROM friends WHERE user_id = ? and friend_id = ?");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, userId);
            ps.setInt(2, friendId);
            rs = ps.executeQuery();
            result =  rs.next();
            logger.info("FriendDAO.removeFriendFromUser() -> Friendship get between {0} and {1}", userId, friendId);
        } catch (Exception e){
            logger.error("FriendDAO.getUserFriends() -> Error fetching friendship", e);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, rs);
        }
        return result;
    }
}
