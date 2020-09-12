package com.paymybuddy.PayMyBuddyWeb.units.models;

import com.paymybuddy.PayMyBuddyWeb.interfaces.service.CountryServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.models.Country;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CountryTest {
    private Country country;

    @Mock
    private static CountryServiceInterface countryService;

    @Tag("CountryTest")
    @Test
    void get_test() throws IOException {
        when(countryService.getNameOfCountry("FRA")).thenReturn("France");
        country = new Country("FRA", countryService);
        verify(countryService, times(1)).getNameOfCountry(anyString());

        assertThat(country.getCode()).isEqualTo("FRA");
        assertThat(country.getWording()).isEqualTo("France");
    }

    @Tag("CountryTest")
    @Test
    void set_test() throws IOException {
        when(countryService.getNameOfCountry("USA")).thenReturn("United States");
        when(countryService.getNameOfCountry("FRA")).thenReturn("France");
        country = new Country("FRA", countryService);
        country.setCountry("USA");
        verify(countryService, times(2)).getNameOfCountry(anyString());

        assertThat(country.getCode()).isEqualTo("USA");
        assertThat(country.getWording()).isEqualTo("United States");
    }
}