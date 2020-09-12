package com.paymybuddy.PayMyBuddyWeb.units.dao;

import com.paymybuddy.PayMyBuddyWeb.DatabaseTestDAO;
import com.paymybuddy.PayMyBuddyWeb.configuration.DatabaseConfiguration;
import com.paymybuddy.PayMyBuddyWeb.dao.TransactionDAO;
import com.paymybuddy.PayMyBuddyWeb.interfaces.DatabaseConfigurationInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.CreditCardDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.TransactionDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.UserDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.models.CreditCard;
import com.paymybuddy.PayMyBuddyWeb.models.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionDAOTest {
    @Mock
    private static CreditCardDAOInterface creditCardDAO;

    private static DatabaseTestDAO databaseTestDAO;

    private TransactionDAOInterface transactionDAO;

    @BeforeAll
    static void init_all(){
        databaseTestDAO = new DatabaseTestDAO();
    }

    @BeforeEach
    void init_each() {
        transactionDAO = new TransactionDAO(databaseTestDAO.getDatabaseConfiguration(), creditCardDAO);
    }

    @Test
    void getUserTransactions() {
        when(creditCardDAO.getCardById(anyInt(), anyInt())).thenReturn(null);

        List<Transaction> transactionList = transactionDAO.getUserTransactions(1);
        assertThat(transactionList.size()).isEqualTo(2);
        assertThat(transactionList.get(0).getAmount()).isEqualTo(100.00);
        assertThat(transactionList.get(1).getAmount()).isEqualTo(1000.00);

        verify(creditCardDAO, times(1)).getCardById(anyInt(), anyInt());
    }

    @Test
    void newTransaction() {

    }

    @AfterEach
    void reset_each() throws SQLException {
        databaseTestDAO.resetDatabase();
    }
}