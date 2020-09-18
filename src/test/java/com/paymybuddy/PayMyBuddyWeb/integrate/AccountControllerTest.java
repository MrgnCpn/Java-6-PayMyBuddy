package com.paymybuddy.PayMyBuddyWeb.integrate;

import com.paymybuddy.PayMyBuddyWeb.MSTestUtils;
import com.paymybuddy.PayMyBuddyWeb.configuration.AppConfiguration;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.*;
import com.paymybuddy.PayMyBuddyWeb.models.*;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ComponentScan({"com.paymybuddy.PayMyBuddyWeb"})
@ContextConfiguration(classes = {AppConfiguration.class})
@WebMvcTest
class AccountControllerTest {

    private MockMvc mockMvc;
    private static User user;
    private static CreditCard creditCard;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private SecurityServiceInterface securityService;

    @MockBean
    private UserServiceInterface userService;

    @MockBean
    private TransactionServiceInterface transactionService;

    @MockBean
    private AccountServiceInterface accountService;

    @MockBean
    private CreditCardServiceInterface creditCardService;

    @BeforeAll
    static void init_all() throws IOException {
        creditCard = new CreditCard(
                1,
                1,
                "VISA",
                "0123456789012345",
                "123",
                "01/20",
                "My Card"
        );

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

        List<CreditCard> creditCardList = new ArrayList();
        creditCardList.add(creditCard);
        creditCardList.add(new CreditCard(
                2,
                1,
                "MAST",
                "0123456789012345",
                "123",
                "01/20",
                "My Card"
        ));
        user.setCreditCards(creditCardList);
    }

    @BeforeEach
    void init_each(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    /**
     * GET
     */

    @Tag("AccountControllerTest")
    @Test
    void GET_getAccount_login_test() throws Exception {
        when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
        when(userService.getUser(any(HttpSession.class))).thenReturn(user);
        when(transactionService.getUserTransactions(any(HttpSession.class))).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/account"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("template.html"))
                .andExpect(model().size(4))
                .andExpect(model().attribute("page", "account"))
                .andExpect(model().attribute("isLogin", true))
                .andExpect(model().attribute("user", user))
                .andExpect(model().attributeExists("transactions"))
        ;

        verify(securityService, times(2)).isLog(any(HttpSession.class));
        verify(userService, times(1)).getUser(any(HttpSession.class));
        verify(transactionService, times(1)).getUserTransactions(any(HttpSession.class));
    }

    @Tag("AccountControllerTest")
    @Test
    void GET_getAccount_logout_test() throws Exception {
        when(securityService.isLog(any(HttpSession.class))).thenReturn(false);
        MSTestUtils.perform_redirect(mockMvc, "GET", "/account", "/");
        verify(securityService, times(1)).isLog(any(HttpSession.class));
    }

    @Tag("AccountControllerTest")
    @Test
    void GET_getFeedAccount_login_test() throws Exception {
        when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
        when(userService.getUser(any(HttpSession.class))).thenReturn(user);

        mockMvc.perform(get("/feed-account"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("template.html"))
                .andExpect(model().size(4))
                .andExpect(model().attribute("page", "feed-account"))
                .andExpect(model().attribute("isLogin", true))
                .andExpect(model().attribute("user", user))
                .andExpect(model().attributeExists("cards"))
        ;

        verify(securityService, times(2)).isLog(any(HttpSession.class));
        verify(userService, times(1)).getUser(any(HttpSession.class));
    }

    @Tag("AccountControllerTest")
    @Test
    void GET_getFeedAccount_logout_test() throws Exception {
        when(securityService.isLog(any(HttpSession.class))).thenReturn(false);
        MSTestUtils.perform_redirect(mockMvc, "GET", "/feed-account", "/");
        verify(securityService, times(1)).isLog(any(HttpSession.class));
    }


    @Tag("AccountControllerTest")
    @Test
    void GET_getCardsList_login_test() throws Exception {
        when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
        when(userService.getUser(any(HttpSession.class))).thenReturn(user);

        mockMvc.perform(get("/manage-card"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("template.html"))
                .andExpect(model().size(3))
                .andExpect(model().attribute("page", "manage-card"))
                .andExpect(model().attribute("isLogin", true))
                .andExpect(model().attributeExists("cards"))
        ;

        verify(securityService, times(2)).isLog(any(HttpSession.class));
        verify(userService, times(1)).getUser(any(HttpSession.class));
    }

    @Tag("AccountControllerTest")
    @Test
    void GET_getCardsList_logout_test() throws Exception {
        when(securityService.isLog(any(HttpSession.class))).thenReturn(false);
        MSTestUtils.perform_redirect(mockMvc, "GET", "/manage-card", "/");
        verify(securityService, times(1)).isLog(any(HttpSession.class));
    }

    @Tag("AccountControllerTest")
    @Test
    void GET_getCard_login_test() throws Exception {
        when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
        when(creditCardService.getUserCardById(any(HttpSession.class), anyInt())).thenReturn(creditCard);

        mockMvc.perform(get("/manage-card/card/edit/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("template.html"))
                .andExpect(model().size(3))
                .andExpect(model().attribute("page", "card"))
                .andExpect(model().attribute("isLogin", true))
                .andExpect(model().attribute("card", creditCard))
        ;

        verify(securityService, times(2)).isLog(any(HttpSession.class));
        verify(creditCardService, times(1)).getUserCardById(any(HttpSession.class), anyInt());
    }

    @Tag("AccountControllerTest")
    @Test
    void GET_getCard_logout_test() throws Exception {
        when(securityService.isLog(any(HttpSession.class))).thenReturn(false);
        MSTestUtils.perform_redirect(mockMvc, "GET", "/manage-card/card/edit/1", "/");
        verify(securityService, times(1)).isLog(any(HttpSession.class));
    }

    @Tag("AccountControllerTest")
    @Test
    void GET_getRemoveCard_login_test() throws Exception {
        when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
        MSTestUtils.perform_redirect(mockMvc, "GET", "/manage-card/card/remove/1", "/manage-card");
        verify(securityService, times(1)).isLog(any(HttpSession.class));
    }

    @Tag("AccountControllerTest")
    @Test
    void GET_getRemoveCard_logout_test() throws Exception {
        when(securityService.isLog(any(HttpSession.class))).thenReturn(false);
        MSTestUtils.perform_redirect(mockMvc, "GET", "/manage-card/card/remove/1", "/");
        verify(securityService, times(1)).isLog(any(HttpSession.class));
    }

    @Tag("AccountControllerTest")
    @Test
    void GET_getNewCard_login_test() throws Exception {
        when(securityService.isLog(any(HttpSession.class))).thenReturn(true);

        mockMvc.perform(get("/manage-card/card/new"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("template.html"))
                .andExpect(model().size(3))
                .andExpect(model().attribute("page", "card"))
                .andExpect(model().attribute("isLogin", true));

        verify(securityService, times(2)).isLog(any(HttpSession.class));
    }

    @Tag("AccountControllerTest")
    @Test
    void GET_getNewCard_logout_test() throws Exception {
        when(securityService.isLog(any(HttpSession.class))).thenReturn(false);
        MSTestUtils.perform_redirect(mockMvc, "GET", "/manage-card/card/new", "/");
        verify(securityService, times(1)).isLog(any(HttpSession.class));
    }

    /**
     * POST
     */

    @Tag("AccountControllerTest")
    @Test
    void POST_postFeedAccount_test() throws Exception {
        MSTestUtils.perform_redirect(mockMvc, "POST", "/feed-account", "/account");
    }

    @Tag("AccountControllerTest")
    @Test
    void POST_postCard_test() throws Exception {
        MSTestUtils.perform_redirect(mockMvc, "POST", "/manage-card/card/edit/1", "/manage-card/card/edit/1");
    }

    @Tag("AccountControllerTest")
    @Test
    void POST_postNewCard_test() throws Exception {
        MSTestUtils.perform_redirect(mockMvc, "POST", "/manage-card/card/new", "/manage-card");
    }
}