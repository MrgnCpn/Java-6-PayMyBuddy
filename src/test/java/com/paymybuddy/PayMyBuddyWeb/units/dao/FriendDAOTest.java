package com.paymybuddy.PayMyBuddyWeb.units.dao;

import com.paymybuddy.PayMyBuddyWeb.DatabaseTestDAO;
import com.paymybuddy.PayMyBuddyWeb.dao.FriendDAO;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.FriendDAOInterface;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    void getUserFriends() {
    }

    @Test
    void addNewFriendToUser() {
    }

    @Test
    void removeFriendFromUser() {
    }

    @Test
    void areFriends() {
    }
}