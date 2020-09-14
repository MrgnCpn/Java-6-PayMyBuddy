package com.paymybuddy.PayMyBuddyWeb.units.dao;

import com.paymybuddy.PayMyBuddyWeb.DatabaseTestDAO;
import com.paymybuddy.PayMyBuddyWeb.dao.FriendDAO;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.FriendDAOInterface;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FriendDAOTest {
    private FriendDAOInterface friendDAO;

    private static DatabaseTestDAO databaseTestDAO;

    @BeforeAll
    static void init_all(){
        databaseTestDAO = new DatabaseTestDAO();
    }

    @BeforeEach
    void init_each(){
        friendDAO = new FriendDAO(databaseTestDAO.getDatabaseConfiguration());
    }

    @Tag("FriendDAOTest")
    @Test
    void getUserFriends_test() {
        List<Integer> friendsList = friendDAO.getUserFriends(1);
        assertThat(friendsList.size()).isEqualTo(1);
        assertThat(friendsList.get(0)).isEqualTo(2);

    }

    @Tag("FriendDAOTest")
    @Test
    void addNewFriendToUser_test() {
        friendDAO.addNewFriendToUser(1, 3);

        List<Integer> friendsList = friendDAO.getUserFriends(1);
        assertThat(friendsList.size()).isEqualTo(2);
        assertThat(friendsList.get(0)).isEqualTo(2);
        assertThat(friendsList.get(1)).isEqualTo(3);
    }

    @Tag("FriendDAOTest")
    @Test
    void removeFriendFromUser_friendsListEmpty_test() throws SQLException {
        friendDAO.removeFriendFromUser(1, 2);

        List<Integer> friendsList = friendDAO.getUserFriends(1);
        assertThat(friendsList).isNull();

        databaseTestDAO.resetDatabase();
    }

    @Tag("FriendDAOTest")
    @Test
    void removeFriendFromUser_friendsListNotEmpty_test() throws SQLException {
        databaseTestDAO.resetDatabase();

        friendDAO.addNewFriendToUser(1, 3);

        List<Integer> friendsList = friendDAO.getUserFriends(1);
        assertThat(friendsList.size()).isEqualTo(2);
        assertThat(friendsList.get(0)).isEqualTo(2);
        assertThat(friendsList.get(1)).isEqualTo(3);

        friendDAO.removeFriendFromUser(1, 2);

        friendsList = friendDAO.getUserFriends(1);
        assertThat(friendsList.size()).isEqualTo(1);
        assertThat(friendsList.get(0)).isEqualTo(3);

        databaseTestDAO.resetDatabase();
    }

    @Tag("FriendDAOTest")
    @Test
    void areFriends_test() {
        assertThat(friendDAO.areFriends(1, 2)).isTrue();
        assertThat(friendDAO.areFriends(1, 3)).isFalse();
    }
}