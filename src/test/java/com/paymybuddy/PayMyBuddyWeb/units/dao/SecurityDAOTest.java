package com.paymybuddy.PayMyBuddyWeb.units.dao;

import com.paymybuddy.PayMyBuddyWeb.DatabaseTestDAO;
import com.paymybuddy.PayMyBuddyWeb.dao.SecurityDAO;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.SecurityDAOInterface;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SecurityDAOTest {
    private SecurityDAOInterface securityDAO;

    private static DatabaseTestDAO databaseTestDAO;

    @BeforeAll
    static void init_all() {
        databaseTestDAO = new DatabaseTestDAO();
    }

    @BeforeEach
    void init_each(){
        securityDAO = new SecurityDAO(databaseTestDAO.getDatabaseConfiguration());
    }

    @Tag("SecurityDAOTest")
    @Test
    void getUserPassword() {
        String password = securityDAO.getUserPassword("juanita.emard@email.com");

        assertThat(password).isNotNull();
        assertThat(password).isNotEmpty();
        assertThat(password).isNotBlank();
    }

    @Tag("SecurityDAOTest")
    @Test
    void updatePassword() throws SQLException {
        String password = securityDAO.getUserPassword("juanita.emard@email.com");

        assertThat(password).isNotNull();
        assertThat(password).isNotEmpty();
        assertThat(password).isNotBlank();
        assertThat(password).isNotEqualTo("password");

        securityDAO.updatePassword(1, "password");
        password = securityDAO.getUserPassword("juanita.emard@email.com");

        assertThat(password).isNotNull();
        assertThat(password).isNotEmpty();
        assertThat(password).isNotBlank();
        assertThat(password.equals("password")).isTrue();

        databaseTestDAO.resetDatabase();
    }
}