package com.paymybuddy.paymybuddyweb.units.services;

import com.paymybuddy.paymybuddyweb.interfaces.dao.AccountDAOInterface;
import com.paymybuddy.paymybuddyweb.interfaces.dao.SecurityDAOInterface;
import com.paymybuddy.paymybuddyweb.interfaces.dao.UserDAOInterface;
import com.paymybuddy.paymybuddyweb.interfaces.service.SecurityServiceInterface;
import com.paymybuddy.paymybuddyweb.models.User;
import com.paymybuddy.paymybuddyweb.services.SecurityService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityServiceTest {
    @Mock
    private HttpSession session;

    private SecurityServiceInterface securityService;

    private static Map<String, Object> signUpParams;
    private static Map<String, Object> incompleteSignUpParams;

    @Mock
    private SecurityDAOInterface securityDAO;

    @Mock
    private UserDAOInterface userDAO;

    @Mock
    private AccountDAOInterface accountDAO;

    private BCryptPasswordEncoder passwordEncoder;

    @BeforeAll
    static void init_all() {
        signUpParams = new HashMap<>();
        signUpParams.put("firstname", "John");
        signUpParams.put("lastName", "Smith");
        signUpParams.put("email", "john.smith@email.com");
        signUpParams.put("password", "password");
        signUpParams.put("age_year", "2000");
        signUpParams.put("age_month", "02");
        signUpParams.put("age_day", "01");
        signUpParams.put("country", "USA");
        signUpParams.put("currency", "USD");

        incompleteSignUpParams = new HashMap<>();
        incompleteSignUpParams.put("firstname", "");
        incompleteSignUpParams.put("lastName", "");
        incompleteSignUpParams.put("email", "");
        incompleteSignUpParams.put("password", "");
        incompleteSignUpParams.put("age_year", "");
        incompleteSignUpParams.put("age_month", "");
        incompleteSignUpParams.put("age_day", "");
        incompleteSignUpParams.put("country", "");
        incompleteSignUpParams.put("currency", "");
    }

    @BeforeEach
    void init_each() {
        passwordEncoder = new BCryptPasswordEncoder();
        securityService = new SecurityService(securityDAO, passwordEncoder, userDAO, accountDAO);
    }

    @Tag("SecurityServiceTest")
    @Test
    void registerUser_test() throws IOException {
        securityService.registerUser(signUpParams);
        verify(userDAO, times(1)).createUser(any(User.class), anyString(), anyString());
    }

    @Tag("SecurityServiceTest")
    @Test
    void registerUser_paramsNull_test() throws IOException {
        securityService.registerUser(null);
        verify(userDAO, never()).createUser(any(User.class), anyString(), anyString());
    }

    @Tag("SecurityServiceTest")
    @Test
    void registerUser_paramsIncomplete_test() throws IOException {
        securityService.registerUser(incompleteSignUpParams);
        verify(userDAO, never()).createUser(any(User.class), anyString(), anyString());

    }
}