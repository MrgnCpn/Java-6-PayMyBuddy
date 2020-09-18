package com.paymybuddy.paymybuddyweb.units.models;

import com.paymybuddy.paymybuddyweb.models.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyTest {
    private Currency currency;

    @BeforeEach
    void init_each() throws IOException {
        currency = new Currency("USD");
    }

    @Tag("CurrencyTest")
    @Test
    void get_test() {
        assertThat(currency.getCode()).isEqualTo("USD");
        assertThat(currency.getRateBasedUSD()).isInstanceOf(Double.class);
        assertThat(currency.getRateBasedUSD()).isPositive();
        assertThat(currency.getRateBasedUSD()).isEqualTo(1);
    }

    @Tag("CurrencyTest")
    @Test
    void set_test() throws IOException {
        currency.setCode("EUR");
        assertThat(currency.getCode()).isEqualTo("EUR");
        assertThat(currency.getRateBasedUSD()).isInstanceOf(Double.class);
        assertThat(currency.getRateBasedUSD()).isPositive();

        currency.setCode("GBP");
        assertThat(currency.getCode()).isEqualTo("GBP");
        assertThat(currency.getRateBasedUSD()).isInstanceOf(Double.class);
        assertThat(currency.getRateBasedUSD()).isPositive();
    }

    @Tag("CurrencyTest")
    @Test
    void getSymbol_test() throws IOException {
        assertThat(currency.getSymbol()).isEqualTo("$");
        currency.setCode("EUR");
        assertThat(currency.getSymbol()).isEqualTo("€");
        currency.setCode("GBP");
        assertThat(currency.getSymbol()).isEqualTo("£");
        currency.setCode("JPY");
        assertThat(currency.getSymbol()).isEqualTo("¥");
    }

    @Tag("CurrencyTest")
    @Test
    void unknownCurrency_test() throws IOException {
        currency.setCode("ZZZ");
        assertThat(currency.getCode()).isNull();
        assertThat(currency.getRateBasedUSD()).isNull();
    }
}