package com.paymybuddy.paymybuddyweb.integrate;

import com.paymybuddy.paymybuddyweb.MSTestUtils;
import com.paymybuddy.paymybuddyweb.configuration.AppConfiguration;
import com.paymybuddy.paymybuddyweb.interfaces.service.SecurityServiceInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.UserServiceInterface;
import com.paymybuddy.paymybuddyweb.models.Country;
import com.paymybuddy.paymybuddyweb.models.User;
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
import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@ComponentScan({"com.paymybuddy.paymybuddyweb" })
@ContextConfiguration(classes = {AppConfiguration.class})
@WebMvcTest
class ProfileControllerTest {

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

    @Tag("ProfileControllerTest")
    @Test
    void GET_getUserProfile_login_test() throws Exception {
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

        mockMvc.perform(get("/profile"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("template.html"))
                .andExpect(model().size(4))
                .andExpect(model().attribute("page", "profile"))
                .andExpect(model().attribute("isLogin", true))
                .andExpect(model().attribute("user", user))
                .andExpect(model().attributeExists("countries"));

        verify(securityService, times(2)).isLog(any(HttpSession.class));
        verify(userService, times(1)).getUser(any(HttpSession.class));
    }

    @Tag("ProfileControllerTest")
    @Test
    void GET_getUserProfile_logout_test() throws Exception {
        when(securityService.isLog(any(HttpSession.class))).thenReturn(false);
        MSTestUtils.perform_redirect(mockMvc, "GET", "/profile", "/");
        verify(securityService, times(1)).isLog(any(HttpSession.class));
    }


    /**
     * POST
     */

    @Tag("ProfileControllerTest")
    @Test
    void POST_postUserProfile_test() throws Exception {
        mockMvc.perform(post("/profile"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));
    }
}