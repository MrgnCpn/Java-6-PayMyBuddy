package com.paymybuddy.PayMyBuddyWeb.units.dao;

import com.paymybuddy.PayMyBuddyWeb.DatabaseTestDAO;
import com.paymybuddy.PayMyBuddyWeb.dao.CreditCardDAO;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.CreditCardDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.models.CreditCard;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

    @Tag("CreditCardDAOTest")
    @Test
    void getUserCreditCards_test() {
        List<CreditCard> creditCardList = creditCardDAO.getUserCreditCards(1);
        assertThat(creditCardList.size()).isEqualTo(2);
        assertThat(creditCardList.get(0).getNumber()).isEqualTo("1447560945069489");
        assertThat(creditCardList.get(1).getNumber()).isEqualTo("2049648612159233");
    }

    @Tag("CreditCardDAOTest")
    @Test
    void getCardById_test() {
        assertThat(creditCardDAO.getCardById(1, 1).getNumber()).isEqualTo("1447560945069489");
        assertThat(creditCardDAO.getCardById(1, 2)).isNull();
    }

    @Tag("CreditCardDAOTest")
    @Test
    void addCreditCard_test() throws SQLException {
        List<CreditCard> creditCardList = creditCardDAO.getUserCreditCards(2);
        assertThat(creditCardList.size()).isEqualTo(1);
        assertThat(creditCardList.get(0).getNumber()).isEqualTo("4251056551139390");

        creditCardDAO.addCreditCard(new CreditCard(null, 2, "VISA", "7036205304409556", "479", "01/20", "My Card - ING"));
        creditCardList = creditCardDAO.getUserCreditCards(2);
        assertThat(creditCardList.size()).isEqualTo(2);
        assertThat(creditCardList.get(0).getNumber()).isEqualTo("4251056551139390");
        assertThat(creditCardList.get(1).getNumber()).isEqualTo("7036205304409556");

        databaseTestDAO.resetDatabase();
    }

    @Tag("CreditCardDAOTest")
    @Test
    void removeCreditCard_test() throws SQLException {
        List<CreditCard> creditCardList = creditCardDAO.getUserCreditCards(1);
        assertThat(creditCardList.size()).isEqualTo(2);
        assertThat(creditCardList.get(0).getNumber()).isEqualTo("1447560945069489");
        assertThat(creditCardList.get(1).getNumber()).isEqualTo("2049648612159233");

        creditCardDAO.removeCreditCard(1, 1);

        creditCardList = creditCardDAO.getUserCreditCards(1);
        assertThat(creditCardList.size()).isEqualTo(1);
        assertThat(creditCardList.get(0).getNumber()).isEqualTo("2049648612159233");

        databaseTestDAO.resetDatabase();
    }

    @Tag("CreditCardDAOTest")
    @Test
    void updateCreditCard_test() throws SQLException {
        List<CreditCard> creditCardList = creditCardDAO.getUserCreditCards(2);
        assertThat(creditCardList.size()).isEqualTo(1);
        assertThat(creditCardList.get(0).getNumber()).isEqualTo("4251056551139390");
        assertThat(creditCardList.get(0).getWording()).isEqualTo("My Card - Bank of America");
        assertThat(creditCardList.get(0).getDate()).isEqualTo("01/20");
        assertThat(creditCardList.get(0).getCvv()).isEqualTo("071");
        assertThat(creditCardList.get(0).getType()).isEqualTo("MAST");

        creditCardDAO.updateCreditCard(new CreditCard(3, 2, "VISA", "7036205304409556", "479", "01/20", "My Card - ING"));

        creditCardList = creditCardDAO.getUserCreditCards(2);
        assertThat(creditCardList.size()).isEqualTo(1);
        assertThat(creditCardList.get(0).getNumber()).isEqualTo("7036205304409556");
        assertThat(creditCardList.get(0).getWording()).isEqualTo("My Card - ING");
        assertThat(creditCardList.get(0).getDate()).isEqualTo("01/20");
        assertThat(creditCardList.get(0).getCvv()).isEqualTo("479");
        assertThat(creditCardList.get(0).getType()).isEqualTo("VISA");

        databaseTestDAO.resetDatabase();
    }
}