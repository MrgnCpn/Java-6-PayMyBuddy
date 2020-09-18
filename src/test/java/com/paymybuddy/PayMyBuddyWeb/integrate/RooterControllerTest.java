package com.paymybuddy.paymybuddyweb.integrate;

import com.paymybuddy.paymybuddyweb.configuration.AppConfiguration;
import com.paymybuddy.paymybuddyweb.interfaces.service.SecurityServiceInterface;
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
@ComponentScan({"com.paymybuddy.paymybuddyweb" })
@ContextConfiguration(classes = {AppConfiguration.class})
@WebMvcTest
class RooterControllerTest {

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

    @Tag("RooterControllerTest")
    @Test
    void GET_root_login_test() throws Exception {
        when(securityService.isLog(any(HttpSession.class))).thenReturn(true);

        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/account"));

        verify(securityService, times(1)).isLog(any(HttpSession.class));
    }

    @Tag("RooterControllerTest")
    @Test
    void GET_root_logout_test() throws Exception {
        when(securityService.isLog(any(HttpSession.class))).thenReturn(false);

        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(securityService, times(1)).isLog(any(HttpSession.class));
    }
}