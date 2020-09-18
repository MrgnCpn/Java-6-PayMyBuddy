package com.paymybuddy.PayMyBuddyWeb.integrate;

import com.paymybuddy.PayMyBuddyWeb.configuration.AppConfiguration;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.SecurityServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.UserServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.models.Country;
import com.paymybuddy.PayMyBuddyWeb.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@ComponentScan({"com.paymybuddy.PayMyBuddyWeb"})
@ContextConfiguration(classes = {AppConfiguration.class})
@WebMvcTest
class TransferControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private SecurityServiceInterface securityService;

    @MockBean
    private UserServiceInterface userService;

    @BeforeEach
    void init_each(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    /**
     * GET
     */

    @Tag("TransferControllerTest")
    @Test
    void GET_getTransfer_login_test() throws Exception {
        User user = new User(
                1,
                "John",
                "Smith",
                LocalDate.of(2000, 02, 01),
                "john.smith@email.com",
                new Country("USA"),
                new ArrayList<>()
        );

        when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
        when(userService.getUser(any(HttpSession.class))).thenReturn(user);
        when(userService.getUserFriends(any(User.class))).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/transfer"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("template.html"))
                .andExpect(model().size(3))
                .andExpect(model().attribute("page", "transfer"))
                .andExpect(model().attribute("isLogin", true))
                .andExpect(model().attributeExists("friends"));

        verify(securityService, times(2)).isLog(any(HttpSession.class));
        verify(userService, times(1)).getUser(any(HttpSession.class));
        verify(userService, times(1)).getUserFriends(any(User.class));
    }

    @Tag("TransferControllerTest")
    @Test
    void GET_getTransfer_logout_test() throws Exception {
        when(securityService.isLog(any(HttpSession.class))).thenReturn(false);

        mockMvc.perform(get("/transfer"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(securityService, times(1)).isLog(any(HttpSession.class));
    }


    /**
     * POST
     */

    @Tag("TransferControllerTest")
    @Test
    void POST_postCard_test() throws Exception {
        mockMvc.perform(post("/transfer"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/account"));
    }
}