package com.paymybuddy.PayMyBuddyWeb.units.models;

import com.paymybuddy.PayMyBuddyWeb.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserTest {
    private User user;

    @Mock
    private static Country country;

    @Mock
    private static Account account;

    @BeforeEach
    void init() {
        List<Integer> friendsList = new ArrayList<>();
        friendsList.add(1);
        friendsList.add(2);
        friendsList.add(3);
        friendsList.add(4);

        user = new User(
                1,
                "John",
                "Smith",
                LocalDate.of(2000, 02, 01),
                "john.smith@email.com",
                country,
                friendsList
        );
    }

    @Tag("UserTest")
    @Test
    void get_test() {
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Smith");
        assertThat(user.getBirthday()).isEqualTo(LocalDate.of(2000, 02, 01));
        assertThat(user.getEmail()).isEqualTo("john.smith@email.com");
        assertThat(user.getCountry()).isEqualTo(country);
        assertThat(user.getFriends().size()).isEqualTo(4);
        assertThat(user.getAccount()).isNull();
        assertThat(user.getCreditCards()).isNull();
        assertThat(user.getCastedBirthday()).isEqualTo("01/02/2000");
        assertThat(user.getAge()).isEqualTo(20);
        assertThat(user.getFullNameCasted(true)).isEqualTo("J.SMITH");
        assertThat(user.getFullNameCasted(false)).isEqualTo("John SMITH");
        assertThat(user.getProfilePictureExt()).isNull();
    }

    @Tag("UserTest")
    @Test
    void set_test() {
        user.setId(10);
        user.setFirstName("William");
        user.setLastName("Blake");
        user.setBirthday(LocalDate.of(2001, 03, 04));
        user.setEmail("william.blake@email.com");
        user.setCreditCards(new ArrayList<CreditCard>());
        user.setCountry(null);
        user.setFriends(null);

        assertThat(user.getId()).isEqualTo(10);
        assertThat(user.getFirstName()).isEqualTo("William");
        assertThat(user.getLastName()).isEqualTo("Blake");
        assertThat(user.getBirthday()).isEqualTo(LocalDate.of(2001, 03, 04));
        assertThat(user.getEmail()).isEqualTo("william.blake@email.com");
        assertThat(user.getCountry()).isNull();
        assertThat(user.getFriends()).isNull();
        assertThat(user.getAccount()).isNull();
        assertThat(user.getCreditCards()).isNotNull();
        assertThat(user.getCastedBirthday()).isEqualTo("04/03/2001");
        assertThat(user.getAge()).isEqualTo(19);
        assertThat(user.getFullNameCasted(true)).isEqualTo("W.BLAKE");
        assertThat(user.getFullNameCasted(false)).isEqualTo("William BLAKE");
    }
}