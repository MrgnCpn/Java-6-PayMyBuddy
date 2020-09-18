package com.paymybuddy.paymybuddyweb.units.services;

import com.paymybuddy.paymybuddyweb.interfaces.dao.CreditCardDAOInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.CreditCardServiceInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.SecurityServiceInterface;
import com.paymybuddy.paymybuddyweb.models.CreditCard;
import com.paymybuddy.paymybuddyweb.services.CreditCardService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditCardServiceTest {
    private CreditCardServiceInterface creditCardService;

    private static Map<String, Object> userInfo;
    private static Map<String, Object> requestParam;
    private static Map<String, Object> incompleteParams;
    private static CreditCard creditCard;

    @Mock
    private HttpSession session;

    @Mock
    private CreditCardDAOInterface creditCardDAO;

    @Mock
    private SecurityServiceInterface securityService;

    @BeforeAll
    static void init_all(){
        userInfo = new HashMap<>();
        userInfo.put("userID", 1);

        requestParam = new HashMap<>();
        requestParam.put("wording", "My Card");
        requestParam.put("cardNumber_1", "0123");
        requestParam.put("cardNumber_2", "4567");
        requestParam.put("cardNumber_3", "8901");
        requestParam.put("cardNumber_4", "2345");
        requestParam.put("cvv", "123");
        requestParam.put("date", "01/20");
        requestParam.put("card_type", "VISA");

        incompleteParams = new HashMap<>();
        incompleteParams.put("wording", "");
        incompleteParams.put("cardNumber_1", "");
        incompleteParams.put("cardNumber_2", "");
        incompleteParams.put("cardNumber_3", "");
        incompleteParams.put("cardNumber_4", "");
        incompleteParams.put("cvv", "");
        incompleteParams.put("date", "");
        incompleteParams.put("card_type", "");

        creditCard = new CreditCard(
                1,
                1,
                "VISA",
                "0123456789012345",
                "123",
                "01/20",
                "My Card"
        );
    }

    @BeforeEach
    void init_each() {
        creditCardService = new CreditCardService(creditCardDAO, securityService);
    }

    @Tag("CreditCardServiceTest")
    @Test
    void getUserCardById_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);
        when(creditCardDAO.getCardById(anyInt(), anyInt())).thenReturn(creditCard);

        assertThat(creditCardService.getUserCardById(session, 1)).isEqualTo(creditCard);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(creditCardDAO, times(1)).getCardById(anyInt(), anyInt());
    }

    @Tag("CreditCardServiceTest")
    @Test
    void getUserCardById_sessionNull_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(null);

        assertThat(creditCardService.getUserCardById(session, 1)).isEqualTo(null);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(creditCardDAO, never()).getCardById(anyInt(), anyInt());
    }

    @Tag("CreditCardServiceTest")
    @Test
    void removeCard_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);

        creditCardService.removeCard(session, 1);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(creditCardDAO, times(1)).removeCreditCard(anyInt(), anyInt());
    }

    @Tag("CreditCardServiceTest")
    @Test
    void removeCard_sessionNull_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(null);

        creditCardService.removeCard(session, 1);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(creditCardDAO, never()).removeCreditCard(anyInt(), anyInt());
    }

    @Tag("CreditCardServiceTest")
    @Test
    void getCardFromRequestParam_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);

        CreditCard card = creditCardService.getCardFromRequestParam(session, 1, requestParam);

        assertThat(card.getId()).isEqualTo(1);
        assertThat(card.getUserId()).isEqualTo(1);
        assertThat(card.getNumber()).isEqualTo("0123456789012345");
        assertThat(card.getType()).isEqualTo("VISA");
        assertThat(card.getCvv()).isEqualTo("123");
        assertThat(card.getDate()).isEqualTo("01/20");
        assertThat(card.getWording()).isEqualTo("My Card");

        verify(securityService, times(1)).getUserInfoFromJWT(session);
    }

    @Tag("CreditCardServiceTest")
    @Test
    void getCardFromRequestParam_paramsIncomplete_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(null);

        assertThat(creditCardService.getCardFromRequestParam(session, 1, incompleteParams)).isNull();

        verify(securityService, times(1)).getUserInfoFromJWT(session);
    }

    @Tag("CreditCardServiceTest")
    @Test
    void getCardFromRequestParam_sessionNull_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(null);

        assertThat(creditCardService.getCardFromRequestParam(session, 1, requestParam)).isNull();

        verify(securityService, times(1)).getUserInfoFromJWT(session);
    }

    @Tag("CreditCardServiceTest")
    @Test
    void updateCard_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);

        creditCardService.updateCard(session, 1, requestParam);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(creditCardDAO, times(1)).updateCreditCard(any(CreditCard.class));
    }

    @Tag("CreditCardServiceTest")
    @Test
    void updateCard_paramsIncomplete_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);

        creditCardService.updateCard(session, 1, incompleteParams);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(creditCardDAO, never()).updateCreditCard(any(CreditCard.class));
    }

    @Tag("CreditCardServiceTest")
    @Test
    void updateCard_sessionNull_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(null);

        creditCardService.updateCard(session, 1, requestParam);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(creditCardDAO, never()).updateCreditCard(any(CreditCard.class));
    }

    @Tag("CreditCardServiceTest")
    @Test
    void addNewCard_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);

        creditCardService.addNewCard(session, requestParam);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(creditCardDAO, times(1)).addCreditCard(any(CreditCard.class));
    }

    @Tag("CreditCardServiceTest")
    @Test
    void addNewCard_paramsIncomplete_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);

        creditCardService.addNewCard(session, incompleteParams);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(creditCardDAO, never()).addCreditCard(any(CreditCard.class));
    }

    @Tag("CreditCardServiceTest")
    @Test
    void addNewCard_sessionNull_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(null);

        creditCardService.addNewCard(session, requestParam);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(creditCardDAO, never()).addCreditCard(any(CreditCard.class));
    }

    @AfterEach
    void reset_each(){
        creditCardService = null;
    }

    @AfterAll
    static void reset_all(){
        userInfo = null;
        requestParam = null;
        incompleteParams = null;
        creditCard = null;
    }
}