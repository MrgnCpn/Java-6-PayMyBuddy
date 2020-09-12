package com.paymybuddy.PayMyBuddyWeb.units.dao;

import com.paymybuddy.PayMyBuddyWeb.DatabaseTestDAO;
import com.paymybuddy.PayMyBuddyWeb.dao.SecurityDAO;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.SecurityDAOInterface;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SecurityDAOTest {
    private SecurityDAOInterface securityDAO;

    private static DatabaseTestDAO databaseTestDAO;

    @BeforeAll
    static void init_all(){
        databaseTestDAO = new DatabaseTestDAO();
    }

    @BeforeEach
    void init_each(){
        securityDAO = new SecurityDAO(databaseTestDAO.getDatabaseConfiguration());
    }

    @Test
    void getUserPassword() {
    }

    @Test
    void updatePassword() {
    }
}