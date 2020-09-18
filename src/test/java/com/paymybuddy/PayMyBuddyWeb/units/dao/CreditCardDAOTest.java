package com.paymybuddy.paymybuddyweb.units.dao;

import com.paymybuddy.paymybuddyweb.DatabaseTestDAO;
import com.paymybuddy.paymybuddyweb.dao.CreditCardDAO;
import com.paymybuddy.paymybuddyweb.interfaces.dao.CreditCardDAOInterface;
import com.paymybuddy.paymybuddyweb.models.CreditCard;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
        assertThat(creditCardList.get(0).getId()).isEqualTo(1);
        assertThat(creditCardList.get(0).getNumber()).isEqualTo("1447560945069489");
        assertThat(creditCardList.get(0).getCvv()).isEqualTo("565");
        assertThat(creditCardList.get(0).getDate()).isEqualTo("01/20");
        assertThat(creditCardList.get(0).getWording()).isEqualTo("My Card - Boursorama");

        assertThat(creditCardList.get(1).getId()).isEqualTo(2);
        assertThat(creditCardList.get(1).getNumber()).isEqualTo("2049648612159233");
        assertThat(creditCardList.get(1).getCvv()).isEqualTo("079");
        assertThat(creditCardList.get(1).getDate()).isEqualTo("01/20");
        assertThat(creditCardList.get(1).getWording()).isEqualTo("My Card - HSBC");

    }

    @Tag("CreditCardDAOTest")
    @Test
    void getCardById_test() {
        CreditCard creditCard = creditCardDAO.getCardById(1, 1);
        assertThat(creditCard.getId()).isEqualTo(1);
        assertThat(creditCard.getNumber()).isEqualTo("1447560945069489");
        assertThat(creditCard.getCvv()).isEqualTo("565");
        assertThat(creditCard.getDate()).isEqualTo("01/20");
        assertThat(creditCard.getWording()).isEqualTo("My Card - Boursorama");

        assertThat(creditCardDAO.getCardById(1, 2)).isNull();
    }

    @Tag("CreditCardDAOTest")
    @Test
    void addCreditCard_test() {
        List<CreditCard> creditCardList = creditCardDAO.getUserCreditCards(2);
        assertThat(creditCardList.size()).isEqualTo(1);
        assertThat(creditCardList.get(0).getId()).isEqualTo(3);
        assertThat(creditCardList.get(0).getNumber()).isEqualTo("4251056551139390");
        assertThat(creditCardList.get(0).getCvv()).isEqualTo("071");
        assertThat(creditCardList.get(0).getDate()).isEqualTo("01/20");
        assertThat(creditCardList.get(0).getWording()).isEqualTo("My Card - Bank of America");

        creditCardDAO.addCreditCard(new CreditCard(null, 2, "VISA", "7036205304409556", "479", "01/20", "My Card - ING"));
        creditCardList = creditCardDAO.getUserCreditCards(2);
        assertThat(creditCardList.size()).isEqualTo(2);
        assertThat(creditCardList.get(0).getId()).isEqualTo(3);
        assertThat(creditCardList.get(0).getNumber()).isEqualTo("4251056551139390");
        assertThat(creditCardList.get(0).getCvv()).isEqualTo("071");
        assertThat(creditCardList.get(0).getDate()).isEqualTo("01/20");
        assertThat(creditCardList.get(0).getWording()).isEqualTo("My Card - Bank of America");

        assertThat(creditCardList.get(1).getId()).isEqualTo(4);
        assertThat(creditCardList.get(1).getNumber()).isEqualTo("7036205304409556");
        assertThat(creditCardList.get(1).getCvv()).isEqualTo("479");
        assertThat(creditCardList.get(1).getDate()).isEqualTo("01/20");
        assertThat(creditCardList.get(1).getWording()).isEqualTo("My Card - ING");
    }

    @Tag("CreditCardDAOTest")
    @Test
    void removeCreditCard_test() {
        List<CreditCard> creditCardList = creditCardDAO.getUserCreditCards(1);
        assertThat(creditCardList.size()).isEqualTo(2);
        assertThat(creditCardList.get(0).getId()).isEqualTo(1);
        assertThat(creditCardList.get(0).getNumber()).isEqualTo("1447560945069489");
        assertThat(creditCardList.get(0).getCvv()).isEqualTo("565");
        assertThat(creditCardList.get(0).getDate()).isEqualTo("01/20");
        assertThat(creditCardList.get(0).getWording()).isEqualTo("My Card - Boursorama");

        assertThat(creditCardList.get(1).getId()).isEqualTo(2);
        assertThat(creditCardList.get(1).getNumber()).isEqualTo("2049648612159233");
        assertThat(creditCardList.get(1).getCvv()).isEqualTo("079");
        assertThat(creditCardList.get(1).getDate()).isEqualTo("01/20");
        assertThat(creditCardList.get(1).getWording()).isEqualTo("My Card - HSBC");

        creditCardDAO.removeCreditCard(1, 1);

        creditCardList = creditCardDAO.getUserCreditCards(1);
        assertThat(creditCardList.size()).isEqualTo(1);
        assertThat(creditCardList.get(0).getNumber()).isEqualTo("2049648612159233");
        assertThat(creditCardList.get(0).getId()).isEqualTo(2);
        assertThat(creditCardList.get(0).getNumber()).isEqualTo("2049648612159233");
        assertThat(creditCardList.get(0).getCvv()).isEqualTo("079");
        assertThat(creditCardList.get(0).getDate()).isEqualTo("01/20");
        assertThat(creditCardList.get(0).getWording()).isEqualTo("My Card - HSBC");
    }

    @Tag("CreditCardDAOTest")
    @Test
    void updateCreditCard_test() {
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
    }

    @AfterEach
    void reset_each() throws SQLException {
        databaseTestDAO.resetDatabase();
    }
}