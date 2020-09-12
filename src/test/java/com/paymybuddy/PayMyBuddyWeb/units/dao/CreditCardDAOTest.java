package com.paymybuddy.PayMyBuddyWeb.units.dao;

import com.paymybuddy.PayMyBuddyWeb.DatabaseTestDAO;
import com.paymybuddy.PayMyBuddyWeb.dao.CreditCardDAO;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.CreditCardDAOInterface;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreditCardDAOTest {
    private CreditCardDAOInterface creditCardDAO;

    private static DatabaseTestDAO databaseTestDAO;

    @BeforeAll
    static void init_all(){
        databaseTestDAO = new DatabaseTestDAO();
    }

    @BeforeEach
    void init_each(){
        creditCardDAO = new CreditCardDAO(databaseTestDAO.getDatabaseConfiguration());
    }

    @Test
    void getUserCreditCards() {
    }

    @Test
    void getCardById() {
    }

    @Test
    void addCreditCard() {
    }

    @Test
    void removeCreditCard() {
    }

    @Test
    void updateCreditCard() {
    }
}