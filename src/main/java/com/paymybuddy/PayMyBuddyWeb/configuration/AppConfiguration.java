package com.paymybuddy.paymybuddyweb.configuration;

import com.paymybuddy.paymybuddyweb.utils.MSControllerUtils;
import com.paymybuddy.paymybuddyweb.dao.*;
import com.paymybuddy.paymybuddyweb.interfaces.DatabaseConfigurationInterface;
import com.paymybuddy.paymybuddyweb.interfaces.Utils.ControllerUtilsInterface;
import com.paymybuddy.paymybuddyweb.interfaces.dao.*;
import com.paymybuddy.paymybuddyweb.interfaces.service.*;
import com.paymybuddy.paymybuddyweb.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Spring Application configuration
 * @author MorganCpn
 */
@Configuration
public class AppConfiguration {
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private DatabaseConfigurationInterface databaseConfiguration = new DatabaseConfiguration("src/main/resources/static/database/databaseConfiguration_prod.properties");

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
        return new TransactionDAO(databaseConfiguration, creditCardDAO());
    }

    @Bean
    public UserDAOInterface userDAO(){
        return new UserDAO(databaseConfiguration, creditCardDAO(), friendDAO(), accountDAO());
    }

    @Bean
    public SecurityServiceInterface securityService(){
        return new SecurityService(securityDAO(), bCryptPasswordEncoder, userDAO(), accountDAO());
    }

    @Bean
    public HTTPRequestServiceInterface httpRequestService() {
        return new HTTPRequestService();
    }

    @Bean
    public CountryServiceInterface countryService(){
        return new CountryService(httpRequestService());
    }

    @Bean
    public UserServiceInterface userService() {
        return new UserService(securityService(), userDAO(), friendDAO());
    }

    @Bean
    public CreditCardServiceInterface creditCardService(){
        return new CreditCardService(creditCardDAO(), securityService());
    }

    @Bean
    public TransactionServiceInterface transactionService() {
        return new TransactionService(securityService(), transactionDAO(), userService());
    }

    @Bean
    public AccountServiceInterface accountService() {
        return new AccountService(securityService(), creditCardService(), transactionService(), accountDAO());
    }

    @Bean
    public ControllerUtilsInterface controllerUtils(){
        return new MSControllerUtils();
    }
}
