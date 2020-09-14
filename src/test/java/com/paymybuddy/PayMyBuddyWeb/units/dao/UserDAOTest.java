package com.paymybuddy.PayMyBuddyWeb.units.dao;

import com.paymybuddy.PayMyBuddyWeb.DatabaseTestDAO;
import com.paymybuddy.PayMyBuddyWeb.dao.UserDAO;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.AccountDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.CreditCardDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.FriendDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.UserDAOInterface;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

@ExtendWith(MockitoExtension.class)
class UserDAOTest {
    private UserDAOInterface userDAO;

    @Mock
    private static CreditCardDAOInterface creditCardDAO;

    @Mock
    private static FriendDAOInterface friendDAO;

    @Mock
    private static AccountDAOInterface accountDAO;

    private static DatabaseTestDAO databaseTestDAO;

    @BeforeAll
    static void init_all(){
        databaseTestDAO = new DatabaseTestDAO();
    }

    @BeforeEach
    void init_each(){
        userDAO = new UserDAO(databaseTestDAO.getDatabaseConfiguration(), creditCardDAO, friendDAO, accountDAO);
    }

    @Tag("UserDAOTest")
    @Test
    void getUserById() {

    }

    @Tag("UserDAOTest")
    @Test
    void getUserByUsername() {
    }

    @Tag("UserDAOTest")
    @Test
    void updateUser() {
    }

    @Tag("UserDAOTest")
    @Test
    void createUser() {
    }

    @Tag("UserDAOTest")
    @Test
    void searchUsers() {
    }
}