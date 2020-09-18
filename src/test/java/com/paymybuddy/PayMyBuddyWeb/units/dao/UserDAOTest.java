package com.paymybuddy.paymybuddyweb.units.dao;

import com.paymybuddy.paymybuddyweb.DatabaseTestDAO;
import com.paymybuddy.paymybuddyweb.dao.UserDAO;
import com.paymybuddy.paymybuddyweb.interfaces.dao.AccountDAOInterface;
import com.paymybuddy.paymybuddyweb.interfaces.dao.CreditCardDAOInterface;
import com.paymybuddy.paymybuddyweb.interfaces.dao.FriendDAOInterface;
import com.paymybuddy.paymybuddyweb.interfaces.dao.UserDAOInterface;
import com.paymybuddy.paymybuddyweb.models.Country;
import com.paymybuddy.paymybuddyweb.models.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
    void getUserById_test() {
        User user = userDAO.getUserById(1);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getFirstName()).isEqualTo("juanita");
        assertThat(user.getLastName()).isEqualTo("emard");
        assertThat(user.getBirthday().getDayOfMonth()).isEqualTo(06);
        assertThat(user.getBirthday().getMonthValue()).isEqualTo(01);
        assertThat(user.getBirthday().getYear()).isEqualTo(1995);
        assertThat(user.getEmail()).isEqualTo("juanita.emard@email.com");
        assertThat(user.getCountry()).isNotNull();
        assertThat(user.getCountry().getCode()).isEqualTo("FRA");
    }

    @Tag("UserDAOTest")
    @Test
    void getUserByUsername_test() {
        User user = userDAO.getUserByUsername("juanita.emard@email.com");

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getFirstName()).isEqualTo("juanita");
        assertThat(user.getLastName()).isEqualTo("emard");
        assertThat(user.getBirthday().getDayOfMonth()).isEqualTo(06);
        assertThat(user.getBirthday().getMonthValue()).isEqualTo(01);
        assertThat(user.getBirthday().getYear()).isEqualTo(1995);
        assertThat(user.getEmail()).isEqualTo("juanita.emard@email.com");
        assertThat(user.getCountry()).isNotNull();
        assertThat(user.getCountry().getCode()).isEqualTo("FRA");
    }

    @Tag("UserDAOTest")
    @Test
    void updateUser_test() throws IOException {
        User user = userDAO.getUserById(1);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getFirstName()).isEqualTo("juanita");
        assertThat(user.getLastName()).isEqualTo("emard");
        assertThat(user.getBirthday().getDayOfMonth()).isEqualTo(06);
        assertThat(user.getBirthday().getMonthValue()).isEqualTo(01);
        assertThat(user.getBirthday().getYear()).isEqualTo(1995);
        assertThat(user.getEmail()).isEqualTo("juanita.emard@email.com");
        assertThat(user.getCountry()).isNotNull();
        assertThat(user.getCountry().getCode()).isEqualTo("FRA");

        user.setFirstName("john");
        user.setLastName("smith");
        user.setEmail("john.smith@email.com");
        user.setBirthday(LocalDate.of(2020, 01, 01));
        user.setCountry(new Country("USA"));

        userDAO.updateUser(user);

        user = userDAO.getUserById(1);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getFirstName()).isEqualTo("john");
        assertThat(user.getLastName()).isEqualTo("smith");
        assertThat(user.getBirthday().getDayOfMonth()).isEqualTo(01);
        assertThat(user.getBirthday().getMonthValue()).isEqualTo(01);
        assertThat(user.getBirthday().getYear()).isEqualTo(2020);
        assertThat(user.getEmail()).isEqualTo("john.smith@email.com");
        assertThat(user.getCountry()).isNotNull();
        assertThat(user.getCountry().getCode()).isEqualTo("USA");
    }

    @Tag("UserDAOTest")
    @Test
    void createUser_test() throws IOException {
        User user = new User(
                0,
                "john",
                "toe",
                LocalDate.of(1990, 02, 02),
                "john.toe@email.com",
                new Country("GBR"),
                null
        );

        userDAO.createUser(user, "USD", "password");
        user = userDAO.getUserByUsername("john.toe@email.com");

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(4);
        assertThat(user.getFirstName()).isEqualTo("john");
        assertThat(user.getLastName()).isEqualTo("toe");
        assertThat(user.getBirthday().getDayOfMonth()).isEqualTo(02);
        assertThat(user.getBirthday().getMonthValue()).isEqualTo(02);
        assertThat(user.getBirthday().getYear()).isEqualTo(1990);
        assertThat(user.getEmail()).isEqualTo("john.toe@email.com");
        assertThat(user.getCountry()).isNotNull();
        assertThat(user.getCountry().getCode()).isEqualTo("GBR");
    }

    @Tag("UserDAOTest")
    @Test
    void searchUsers_test() {
        List<User> userList = userDAO.searchUsers(0, "ju");

        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(1);
        assertThat(userList.get(0).getId()).isEqualTo(1);
        assertThat(userList.get(0).getFirstName()).isEqualTo("juanita");
        assertThat(userList.get(0).getLastName()).isEqualTo("emard");
        assertThat(userList.get(0).getBirthday()).isNull();
        assertThat(userList.get(0).getEmail()).isEqualTo("juanita.emard@email.com");
        assertThat(userList.get(0).getCountry()).isNull();
    }

    @AfterEach
    void reset_each() throws SQLException {
        databaseTestDAO.resetDatabase();
    }
}