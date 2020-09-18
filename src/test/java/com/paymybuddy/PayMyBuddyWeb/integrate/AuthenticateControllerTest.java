package com.paymybuddy.PayMyBuddyWeb.integrate;

import com.paymybuddy.PayMyBuddyWeb.MSTestUtils;
import com.paymybuddy.PayMyBuddyWeb.configuration.AppConfiguration;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.SecurityServiceInterface;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ComponentScan({"com.paymybuddy.PayMyBuddyWeb"})
@ContextConfiguration(classes = {AppConfiguration.class})
@WebMvcTest
class AuthenticateControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private SecurityServiceInterface securityService;

    @BeforeEach
    void init_each(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    /**
     * GET
     */

    @Tag("AuthenticateControllerTest")
    @Test
    void GET_getLogin_logout_test() throws Exception {
        when(securityService.isLog(any(HttpSession.class))).thenReturn(false);

        mockMvc.perform(get("/login"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("template.html"))
                .andExpect(model().size(2))
                .andExpect(model().attribute("page", "login"))
                .andExpect(model().attribute("isLogin", false));

        verify(securityService, times(2)).isLog(any(HttpSession.class));
    }

    @Tag("AuthenticateControllerTest")
    @Test
    void GET_getLogin_login_test() throws Exception {
        when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
        MSTestUtils.perform_redirect(mockMvc, "GET", "/login", "/");
        verify(securityService, times(1)).isLog(any(HttpSession.class));
    }

    @Tag("AuthenticateControllerTest")
    @Test
    void GET_getSignup_logout_test() throws Exception {
        when(securityService.isLog(any(HttpSession.class))).thenReturn(false);

        mockMvc.perform(get("/signup"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("template.html"))
                .andExpect(model().size(3))
                .andExpect(model().attribute("page", "signup"))
                .andExpect(model().attribute("isLogin", false))
                .andExpect(model().attributeExists("countries"));

        verify(securityService, times(2)).isLog(any(HttpSession.class));
    }

    @Tag("AuthenticateControllerTest")
    @Test
    void GET_getSignup_login_test() throws Exception {
        when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
        MSTestUtils.perform_redirect(mockMvc, "GET", "/signup", "/");
        verify(securityService, times(1)).isLog(any(HttpSession.class));
    }

    @Tag("AuthenticateControllerTest")
    @Test
    void GET_logout_test() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }


    /**
     * POST
     */

    @Tag("AuthenticateControllerTest")
    @Test
    void POST_postLogin_test() throws Exception {
        MSTestUtils.perform_redirect(mockMvc, "POST", "/login", "/");
    }

    @Tag("AuthenticateControllerTest")
    @Test
    void POST_postSignup_test() throws Exception {
        MSTestUtils.perform_redirect(mockMvc, "POST", "/signup", "/");
    }

    @Tag("AuthenticateControllerTest")
    @Test
    void POST_changePassword_test() throws Exception {
        MSTestUtils.perform_redirect(mockMvc, "POST", "/change-password", "/profile");
    }
}