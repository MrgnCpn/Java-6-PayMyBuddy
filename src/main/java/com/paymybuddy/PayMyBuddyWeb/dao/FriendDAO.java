package com.paymybuddy.PayMyBuddyWeb.dao;

import com.paymybuddy.PayMyBuddyWeb.interfaces.DatabaseConfigurationInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.FriendDAOInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class FriendDAO implements FriendDAOInterface {
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
    public FriendDAO(DatabaseConfigurationInterface databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
    }

    /**
     * @see FriendDAOInterface {@link #getUserFriends(Integer)}
     */
    @Override
    public List<Integer> getUserFriends(Integer userId) {
        List<Integer> result = new ArrayList<>();
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement ps = null;

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT friend_id FROM friends WHERE user_id = ?");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                result.add(rs.getInt("friend_id"));
            }
            logger.info("FriendDAO.getUserFriends() -> Friends getted for user : " + userId);
        } catch (Exception e){
            logger.error("FriendDAO.getUserFriends() -> Error fetching user", e);
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

        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO friends (user_id, friend_id)");
        sql.append(" VALUES (?, ?)");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, userId);
            ps.setInt(2, friendId);
            ps.execute();

            ps.setInt(1, friendId);
            ps.setInt(2, userId);
            ps.execute();

            logger.info("FriendDAO.addNewFriendToUser() -> Friendship added between " + userId + " and " + friendId);
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

        StringBuffer sql = new StringBuffer();
        sql.append("DELETE friends");
        sql.append(" WHERE user_id = ? AND friend_id = ?");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, userId);
            ps.setInt(2, friendId);
            ps.execute();
            logger.info("FriendDAO.removeFriendFromUser() -> Friendship removed between " + userId + " and " + friendId);
        } catch (Exception ex){
            logger.error("FriendDAO.removeFriendFromUser() -> Error remove user friend", ex);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, null);

        }
    }
}
