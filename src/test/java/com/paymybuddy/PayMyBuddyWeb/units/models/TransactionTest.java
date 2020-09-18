package com.paymybuddy.paymybuddyweb.units.models;

import com.paymybuddy.paymybuddyweb.utils.MSNumberUtils;
import com.paymybuddy.paymybuddyweb.models.CreditCard;
import com.paymybuddy.paymybuddyweb.models.Currency;
import com.paymybuddy.paymybuddyweb.models.Transaction;
import com.paymybuddy.paymybuddyweb.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TransactionTest {
    private Transaction transaction;

    @Mock
    private CreditCard creditCard;

    @Mock
    private CreditCard setCreditCard;

    private Currency currency;

    @BeforeEach
    void init_each() throws IOException {
        currency = new Currency("USD");
        User userFrom = new User();
        userFrom.setId(10);
        User userTo = new User();
        userTo.setId(20);

        transaction = new Transaction (
                creditCard,
                userFrom,
                userTo,
                LocalDate.of(2020, 01, 01),
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusantium consectetur deserunt eum ex hic iste iusto maiores mollitia nisi optio quam, qui quidem quod sequi similique sit voluptatem. Dolore, quidem.",
                1000.0,
                currency
        );
    }

    @Tag("TransactionTest")
    @Test
    void get_test(){
        assertThat(transaction.getCard()).isEqualTo(creditCard);
        assertThat(transaction.getUserFrom().getId()).isEqualTo(10);
        assertThat(transaction.getUserTo().getId()).isEqualTo(20);
        assertThat(transaction.getDate()).isEqualTo(LocalDate.of(2020, 01, 01));
        assertThat(transaction.getDescription()).isEqualTo("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusantium consectetur deserunt eum ex hic iste iusto maiores mollitia nisi optio quam, qui quidem quod sequi similique sit voluptatem. Dolore, quidem.");
        assertThat(transaction.getAmount()).isEqualTo(MSNumberUtils.getDoubleTwoDigits(1000.0));
        assertThat(transaction.getFee()).isEqualTo(MSNumberUtils.getDoubleTwoDigits(1000.0 * 0.005));
        assertThat(transaction.getFinalAmount()).isEqualTo(MSNumberUtils.getDoubleTwoDigits(1000.0 - (1000.0 * 0.005)));
        assertThat(transaction.getCurrency()).isEqualTo(currency);
        assertThat(transaction.getHistoryDescription()).isEqualTo("Lorem ipsum dolor sit amet, consectetur adipisicin...");
        assertThat(transaction.getContactUser(10)).isInstanceOf(User.class);
        assertThat(transaction.getContactUser(20)).isInstanceOf(User.class);
        assertThat(transaction.getContactUser(30)).isNull();
        assertThat(transaction.getTransactionLib(10)).isEqualTo("- 1005.0 $");
        assertThat(transaction.getTransactionLib(20)).isEqualTo("+ 995.0 $");
        assertThat(transaction.getTransactionLib(30)).isNull();
    }

    @Tag("TransactionTest")
    @Test
    void set_test() throws IOException {
        Currency setCurrency = new Currency("USD");
        User userFrom = new User();
        userFrom.setId(111);
        User userTo = new User();
        userTo.setId(222);
        transaction.setCard(setCreditCard);
        transaction.setUserFrom(userFrom);
        transaction.setUserTo(userTo);
        transaction.setDate(LocalDate.of(2020, 04, 03));
        transaction.setDescription("Accusantium consectetur deserunt eum ex hic iste iusto maiores mollitia nisi optio quam, qui quidem quod sequi similique sit voluptatem. Dolore, quidem.");
        transaction.setAmount(2222.22);
        transaction.setCurrency(setCurrency);


        assertThat(transaction.getCard()).isEqualTo(setCreditCard);
        assertThat(transaction.getUserFrom().getId()).isEqualTo(111);
        assertThat(transaction.getUserTo().getId()).isEqualTo(222);
        assertThat(transaction.getDate()).isEqualTo(LocalDate.of(2020, 04, 03));
        assertThat(transaction.getDescription()).isEqualTo("Accusantium consectetur deserunt eum ex hic iste iusto maiores mollitia nisi optio quam, qui quidem quod sequi similique sit voluptatem. Dolore, quidem.");
        assertThat(transaction.getAmount()).isEqualTo(MSNumberUtils.getDoubleTwoDigits(2222.22));
        assertThat(transaction.getFee()).isEqualTo(MSNumberUtils.getDoubleTwoDigits(2222.22 * 0.005));
        assertThat(transaction.getFinalAmount()).isEqualTo(MSNumberUtils.getDoubleTwoDigits(2222.22 - (2222.22 * 0.005)));
        assertThat(transaction.getCurrency()).isEqualTo(setCurrency);
        assertThat(transaction.getHistoryDescription()).isEqualTo("Accusantium consectetur deserunt eum ex hic iste i...");
        assertThat(transaction.getContactUser(111)).isInstanceOf(User.class);
        assertThat(transaction.getContactUser(222)).isInstanceOf(User.class);
        assertThat(transaction.getContactUser(333)).isNull();
        assertThat(transaction.getTransactionLib(111)).isEqualTo("- 2233.33 $");
        assertThat(transaction.getTransactionLib(222)).isEqualTo("+ 2211.11 $");
        assertThat(transaction.getTransactionLib(333)).isNull();
    }
}