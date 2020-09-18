package com.paymybuddy.paymybuddyweb.units.services;

import com.paymybuddy.paymybuddyweb.interfaces.dao.AccountDAOInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.AccountServiceInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.CreditCardServiceInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.SecurityServiceInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.TransactionServiceInterface;
import com.paymybuddy.paymybuddyweb.models.*;
import com.paymybuddy.paymybuddyweb.services.AccountService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    private AccountServiceInterface accountService;

    private static Map<String, Object> userInfo;
    private static Map<String, Object> requestParam;
    private static User user;
    private static CreditCard creditCard;

    @Mock
    private SecurityServiceInterface securityService;

    @Mock
    private TransactionServiceInterface transactionService;

    @Mock
    private CreditCardServiceInterface creditCardService;

    @Mock
    private AccountDAOInterface accountDAO;

    @Mock
    private HttpSession session;

    @BeforeAll
    static void init_all() throws IOException {
        userInfo = new HashMap<>();
        userInfo.put("userID", 1);

        requestParam = new HashMap<>();
        requestParam.put("cardid", "1");
        requestParam.put("amount", "200");

        user = new User(
                1,
                "John",
                "Smith",
                LocalDate.of(2000, 02, 01),
                "john.smith@email.com",
                new Country("USA"),
                new ArrayList<>()
        );

        user.setAccount(new Account(1, 1000.00, new Currency("USD"), LocalDate.now()));

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
        accountService = new AccountService(securityService, creditCardService, transactionService, accountDAO);
    }

    @Tag("AccountServiceTest")
    @Test
    void feedAccount_test() throws SQLException {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);
        when(creditCardService.getUserCardById(eq(session), anyInt())).thenReturn(creditCard);

        accountService.feedAccount(session, requestParam, user);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(creditCardService, times(1)).getUserCardById(eq(session), anyInt());
        verify(transactionService, times(1)).feedAccountTransaction(any(Transaction.class));
        verify(accountDAO, times(1)).updateAccount(any(Account.class));
    }

    @Tag("AccountServiceTest")
    @Test
    void feedAccount_sessionNull_test() throws SQLException {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(null);

        accountService.feedAccount(session, null, user);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(creditCardService, never()).getUserCardById(eq(session), anyInt());
        verify(transactionService, never()).feedAccountTransaction(any(Transaction.class));
        verify(accountDAO, never()).updateAccount(any(Account.class));
    }

    @Tag("AccountServiceTest")
    @Test
    void feedAccount_requestParamNull_test() throws SQLException {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);

        accountService.feedAccount(session, null, user);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(creditCardService, never()).getUserCardById(eq(session), anyInt());
        verify(transactionService, never()).feedAccountTransaction(any(Transaction.class));
        verify(accountDAO, never()).updateAccount(any(Account.class));
    }

    @Tag("AccountServiceTest")
    @Test
    void feedAccount_cardNull_test() throws SQLException {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);
        when(creditCardService.getUserCardById(eq(session), anyInt())).thenReturn(null);

        accountService.feedAccount(session, requestParam, user);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(creditCardService, times(1)).getUserCardById(eq(session), anyInt());
        verify(transactionService, never()).feedAccountTransaction(any(Transaction.class));
        verify(accountDAO, never()).updateAccount(any(Account.class));
    }

    @Tag("AccountServiceTest")
    @Test
    void feedAccount_amountEqualZero_test() throws SQLException {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);
        when(creditCardService.getUserCardById(eq(session), anyInt())).thenReturn(creditCard);

        Map<String, Object> l_requestParams = new HashMap<>();
        l_requestParams.put("cardid", "1");
        l_requestParams.put("amount", "0");

        accountService.feedAccount(session, l_requestParams, user);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(creditCardService, times(1)).getUserCardById(eq(session), anyInt());
        verify(transactionService, never()).feedAccountTransaction(any(Transaction.class));
        verify(accountDAO, never()).updateAccount(any(Account.class));
    }

    @AfterEach
    void reset_each(){
        accountService = null;
    }

    @AfterAll
    static void reset_all(){
        user = null;
        creditCard = null;
        requestParam = null;
        userInfo = null;
    }
}