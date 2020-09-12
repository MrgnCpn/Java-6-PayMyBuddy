package com.paymybuddy.PayMyBuddyWeb.units.dao;

import com.paymybuddy.PayMyBuddyWeb.DatabaseTestDAO;
import com.paymybuddy.PayMyBuddyWeb.dao.AccountDAO;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.AccountDAOInterface;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountDAOTest {
    private AccountDAOInterface accountDAO;

    private static DatabaseTestDAO databaseTestDAO;

    @BeforeAll
    static void init_all(){
        databaseTestDAO = new DatabaseTestDAO();
    }

    @BeforeEach
    void init_each(){
        accountDAO = new AccountDAO(databaseTestDAO.getDatabaseConfiguration());
    }

    @Test
    void getAccount() {
    }

    @Test
    void updateAccount() {
    }

    @Test
    void createAccount() {
    }
}