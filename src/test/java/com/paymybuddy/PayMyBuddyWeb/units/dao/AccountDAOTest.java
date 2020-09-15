package com.paymybuddy.PayMyBuddyWeb.units.dao;

import com.paymybuddy.PayMyBuddyWeb.DatabaseTestDAO;
import com.paymybuddy.PayMyBuddyWeb.dao.AccountDAO;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.AccountDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.models.Account;
import com.paymybuddy.PayMyBuddyWeb.models.Currency;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Tag("AccountDAOTest")
    @Test
    void getAccount_test() {
        Account account = accountDAO.getAccount(1);

        assertThat(account.getUserId()).isEqualTo(1);
        assertThat(account.getAmount()).isEqualTo(895.00);
        assertThat(account.getCurrency().getCode()).isEqualTo("USD");
        assertThat(account.getDate()).isEqualTo(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()));
    }

    @Tag("AccountDAOTest")
    @Test
    void updateAccount_test() throws IOException {
        Account account = accountDAO.getAccount(1);

        assertThat(account.getUserId()).isEqualTo(1);
        assertThat(account.getAmount()).isEqualTo(895.00);
        assertThat(account.getCurrency().getCode()).isEqualTo("USD");
        assertThat(account.getDate()).isEqualTo(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()));

        account.setCurrency(new Currency("EUR"));
        account.setAmount(8000.00);
        account.setDate(LocalDate.of(1990, 10, 11));

        accountDAO.updateAccount(account);

        account = accountDAO.getAccount(1);

        assertThat(account.getUserId()).isEqualTo(1);
        assertThat(account.getAmount()).isEqualTo(8000.00);
        assertThat(account.getCurrency().getCode()).isEqualTo("EUR");
        assertThat(account.getDate()).isEqualTo(LocalDate.of(1990, 10, 11));
    }

    @Tag("AccountDAOTest")
    @Test
    void createAccount_test() {
        accountDAO.createAccount(3, "EUR");
        Account account = accountDAO.getAccount(3);

        assertThat(account.getUserId()).isEqualTo(3);
        assertThat(account.getAmount()).isZero();
        assertThat(account.getCurrency().getCode()).isEqualTo("EUR");
        assertThat(account.getDate()).isEqualTo(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()));
    }

    @AfterEach
    void reset_each() throws SQLException {
        databaseTestDAO.resetDatabase();
    }
}