package com.paymybuddy.PayMyBuddyWeb.configuration;

import com.paymybuddy.PayMyBuddyWeb.controllers.ControllerUtil;
import com.paymybuddy.PayMyBuddyWeb.dao.*;
import com.paymybuddy.PayMyBuddyWeb.interfaces.DatabaseConfigurationInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.*;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.SecurityServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.services.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfiguration {
    private ControllerUtil controllerUtil = new ControllerUtil();
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private DatabaseConfigurationInterface databaseConfiguration = new DatabaseConfiguration();

    @Bean
    public AccountDAOInterface accountDAO(){
        return new AccountDAO(databaseConfiguration);
    }

    @Bean
    public CreditCardDAOInterface creditCardDAO(){
        return new CreditCardDAO(databaseConfiguration);
    }

    @Bean
    public FriendDAOInterface friendDAO(){
        return new FriendDAO(databaseConfiguration);
    }

    @Bean
    public SecurityDAOInterface securityDAO(){
        return new SecurityDAO(databaseConfiguration);
    }

    @Bean
    public TransactionDAOInterface transactionDAO(){
        return new TransactionDAO(databaseConfiguration);
    }

    @Bean
    public UserDAOInterface userDAO(){
        return new UserDAO(databaseConfiguration, creditCardDAO(), friendDAO(), accountDAO());
    }

    @Bean
    public SecurityServiceInterface securityService(){
        return new SecurityService(securityDAO(), bCryptPasswordEncoder, userDAO());
    }
}
