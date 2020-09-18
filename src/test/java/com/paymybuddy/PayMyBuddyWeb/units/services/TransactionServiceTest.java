package com.paymybuddy.paymybuddyweb.units.services;

import com.paymybuddy.paymybuddyweb.interfaces.dao.TransactionDAOInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.SecurityServiceInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.TransactionServiceInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.UserServiceInterface;
import com.paymybuddy.paymybuddyweb.models.Transaction;
import com.paymybuddy.paymybuddyweb.models.User;
import com.paymybuddy.paymybuddyweb.services.TransactionService;
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
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private SecurityServiceInterface securityService;

    @Mock
    private TransactionDAOInterface transactionDAO;

    @Mock
    private UserServiceInterface userService;

    @Mock
    private HttpSession session;

    private static Map<String, Object> userInfo;
    private static Map<String, Object> requestParam;
    private static List<Transaction> transactionList;
    private static Transaction transaction;

    private TransactionServiceInterface transactionService;

    @BeforeAll
    static void init_all(){
        userInfo = new HashMap<>();
        userInfo.put("userID", 1);

        requestParam = new HashMap<>();
        requestParam.put("to", "20");
        requestParam.put("amount", "200");
        requestParam.put("currency", "EUR");
        requestParam.put("description", "Description");

        User userFrom = new User();
        userFrom.setId(10);
        User userTo = new User();
        userTo.setId(20);

        transaction = new Transaction (
                null,
                userFrom,
                userTo,
                LocalDate.of(2020, 01, 01),
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusantium consectetur deserunt eum ex hic iste iusto maiores mollitia nisi optio quam, qui quidem quod sequi similique sit voluptatem. Dolore, quidem.",
                1000.0,
                null
        );

        transactionList = new ArrayList<>();
        transactionList.add(transaction);
    }

    @BeforeEach
    void init_each() {
        transactionService = new TransactionService(securityService, transactionDAO, userService);
    }

    @Tag("TransactionServiceTest")
    @Test
    void getUserTransaction_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);
        when(transactionDAO.getUserTransactions(anyInt())).thenReturn(transactionList);

        List<Transaction> transactionListReturn = transactionService.getUserTransactions(session);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(transactionDAO, times(1)).getUserTransactions(anyInt());

        assertThat(transactionListReturn).isNotNull();
        assertThat(transactionListReturn.size()).isEqualTo(1);
        assertThat(transactionListReturn.get(0).getUserFrom().getId()).isEqualTo(10);
    }

    @Tag("TransactionServiceTest")
    @Test
    void feedAccountTransaction_test() throws SQLException {
        transactionService.feedAccountTransaction(transaction);
        verify(transactionDAO, times(1)).newTransaction(any(Transaction.class));
    }

    @Tag("TransactionServiceTest")
    @Test
    void doTransfer_friends_test() throws IOException, SQLException {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);
        when(userService.getIfAreFriends(eq(session), anyInt())).thenReturn(true);
        when(userService.getUser(anyInt())).thenReturn(null);

        transactionService.doTransfer(session, requestParam);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(userService, times(1)).getIfAreFriends(eq(session), anyInt());
        verify(userService, times(2)).getUser(anyInt());
        verify(transactionDAO, times(1)).newTransaction(any(Transaction.class));
    }

    @Tag("TransactionServiceTest")
    @Test
    void doTransfer_noFriends_test() throws IOException, SQLException {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);
        when(userService.getIfAreFriends(eq(session), anyInt())).thenReturn(false);

        transactionService.doTransfer(session, requestParam);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(userService, times(1)).getIfAreFriends(eq(session), anyInt());
        verify(userService, never()).getUser(anyInt());
        verify(transactionDAO, never()).newTransaction(any(Transaction.class));
    }

    @AfterEach
    void reset_each(){
        transactionService = null;
    }

    @AfterAll
    static void reset_All(){
        userInfo = null;
        requestParam = null;
        transaction = null;
        transactionList = null;
    }
}