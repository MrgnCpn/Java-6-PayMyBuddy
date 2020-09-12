package com.paymybuddy.PayMyBuddyWeb.units.models;

import com.paymybuddy.PayMyBuddyWeb.models.Account;
import com.paymybuddy.PayMyBuddyWeb.models.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountTest {
    private Account account;

    @Mock
    private static Currency currency;

    @Mock
    private static Currency currencySet;

    @BeforeEach
    void init() {
        account = new Account(
                1,
                1234.56,
                currency,
                LocalDate.of(2020, 01, 01)
        );
    }

    @Tag("AccountTest")
    @Test
    void get_test() {
        assertThat(account.getUserId()).isEqualTo(1);
        assertThat(account.getAmount()).isEqualTo(1234.56);
        assertThat(account.getCurrency()).isEqualTo(currency);
        assertThat(account.getDate()).isEqualTo(LocalDate.of(2020, 01, 01));

        when(currency.getSymbol()).thenReturn("$");
        assertThat(account.getAmountLib()).isEqualTo("+ 1234.56 $");
        verify(currency, times(1)).getSymbol();
    }

    @Tag("AccountTest")
    @Test
    void set_test() {
        account.setUserId(10);
        account.setAmount(7890.12);
        account.setDate(LocalDate.of(2021, 02,02));
        account.setCurrency(currencySet);

        assertThat(account.getUserId()).isEqualTo(10);
        assertThat(account.getAmount()).isEqualTo(7890.12);
        assertThat(account.getCurrency()).isEqualTo(currencySet);
        assertThat(account.getDate()).isEqualTo(LocalDate.of(2021, 02, 02));

        when(currencySet.getSymbol()).thenReturn("$");
        assertThat(account.getAmountLib()).isEqualTo("+ 7890.12 $");
        verify(currencySet, times(1)).getSymbol();
    }
}