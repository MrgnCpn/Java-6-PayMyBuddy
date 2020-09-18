package com.paymybuddy.PayMyBuddyWeb.units.dao;

import com.paymybuddy.PayMyBuddyWeb.DatabaseTestDAO;
import com.paymybuddy.PayMyBuddyWeb.Utils.MSNumberUtils;
import com.paymybuddy.PayMyBuddyWeb.configuration.DatabaseConfiguration;
import com.paymybuddy.PayMyBuddyWeb.dao.TransactionDAO;
import com.paymybuddy.PayMyBuddyWeb.interfaces.DatabaseConfigurationInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.CreditCardDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.TransactionDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.UserDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.models.CreditCard;
import com.paymybuddy.PayMyBuddyWeb.models.Currency;
import com.paymybuddy.PayMyBuddyWeb.models.Transaction;
import com.paymybuddy.PayMyBuddyWeb.models.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionDAOTest {
    private TransactionDAOInterface transactionDAO;

    @Mock
    private static CreditCardDAOInterface creditCardDAO;

    private static DatabaseTestDAO databaseTestDAO;

    @BeforeAll
    static void init_all(){
        databaseTestDAO = new DatabaseTestDAO();
    }

    @BeforeEach
    void init_each() {
        transactionDAO = new TransactionDAO(databaseTestDAO.getDatabaseConfiguration(), creditCardDAO);
    }

    @Tag("TransactionDAOTest")
    @Test
    void getUserTransactions() {
        when(creditCardDAO.getCardById(anyInt(), anyInt())).thenReturn(null);

        List<Transaction> transactionList = transactionDAO.getUserTransactions(1);
        assertThat(transactionList.size()).isEqualTo(2);

        assertThat(transactionList.get(0).getAmount()).isEqualTo(100.00);
        assertThat(transactionList.get(1).getAmount()).isEqualTo(1000.00);

        verify(creditCardDAO, times(1)).getCardById(anyInt(), anyInt());
    }

    @Tag("TransactionDAOTest")
    @Test
    void newTransaction() throws IOException, SQLException {
        User userFrom = new User();
        userFrom.setId(1);
        User userTo = new User();
        userTo.setId(2);

        Transaction transaction = new Transaction (
                null,
                userFrom,
                userTo,
                LocalDate.of(2020, 01, 01),
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusantium consectetur deserunt eum ex hic iste iusto maiores mollitia nisi optio quam, qui quidem quod sequi similique sit voluptatem. Dolore, quidem.",
                2222.22,
                new Currency("USD")
        );

        transactionDAO.newTransaction(transaction);

        List<Transaction> transactionList = transactionDAO.getUserTransactions(1);
        assertThat(transactionList.size()).isEqualTo(3);
        assertThat(transactionList.get(0).getAmount()).isEqualTo(2222.22);
        assertThat(transactionList.get(1).getAmount()).isEqualTo(100.00);
        assertThat(transactionList.get(2).getAmount()).isEqualTo(1000.00);
    }

    @AfterEach
    void reset_each() throws SQLException {
        databaseTestDAO.resetDatabase();
    }
}