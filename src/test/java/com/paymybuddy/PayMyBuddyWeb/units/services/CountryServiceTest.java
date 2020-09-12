package com.paymybuddy.PayMyBuddyWeb.units.services;

import com.paymybuddy.PayMyBuddyWeb.interfaces.service.CountryServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.HTTPRequestServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.services.CountryService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {
    private CountryServiceInterface countryService;

    @Mock
    private static HTTPRequestServiceInterface httpRequestService;

    @BeforeEach
    void init() {
        countryService = new CountryService(httpRequestService);
    }

    @Tag("CountryServiceTest")
    @Test
    void getNameOfCountry_200_test() throws IOException, JSONException {
        when(httpRequestService.getReq(anyString(), anyMap())).thenReturn(new JSONObject("{\"content\": {\"name\":\"United Kingdom of Great Britain and Northern Ireland\"},\"status\":\"200\"}"));

        assertThat(countryService.getNameOfCountry("")).isEqualTo("United Kingdom of Great Britain and Northern Ireland");

        verify(httpRequestService, times(1)).getReq(anyString(), any(HashMap.class));
    }

    @Tag("CountryServiceTest")
    @Test
    void getNameOfCountry_400_test() throws IOException, JSONException {
        when(httpRequestService.getReq(anyString(), anyMap())).thenReturn(new JSONObject("{\"content\": {\"name\":\"United Kingdom of Great Britain and Northern Ireland\"},\"status\":\"400\"}"));

        assertThat(countryService.getNameOfCountry("")).isNull();

        verify(httpRequestService, times(1)).getReq(anyString(), any(HashMap.class));
    }

    @Tag("CountryServiceTest")
    @Test
    void getAllCountries_200_test() throws JSONException, IOException {
        StringBuffer responseData_listCountry = new StringBuffer();
        responseData_listCountry.append("{\"content\": [{\"name\":\"Afghanistan\",\"alpha3Code\":\"AFG\"},");
        responseData_listCountry.append("{\"name\":\"Åland Islands\",\"alpha3Code\":\"ALA\"},");
        responseData_listCountry.append("{\"name\":\"Albania\",\"alpha3Code\":\"ALB\"},");
        responseData_listCountry.append("{\"name\":\"Algeria\",\"alpha3Code\":\"DZA\"}],");
        responseData_listCountry.append("\"status\":\"200\"}");

        when(httpRequestService.getReq(anyString(), eq(null))).thenReturn(new JSONObject(responseData_listCountry.toString()));

        Map<String, String> result = countryService.getAllCountries();

        assertThat(result.size()).isEqualTo(4);
        assertThat(result.get("AFG")).isEqualTo("Afghanistan");
        assertThat(result.get("ALA")).isEqualTo("Åland Islands");
        assertThat(result.get("ALB")).isEqualTo("Albania");
        assertThat(result.get("DZA")).isEqualTo("Algeria");

        verify(httpRequestService, times(1)).getReq(anyString(), eq(null));

    }

    @Tag("CountryServiceTest")
    @Test
    void getAllCountries_400_test() throws JSONException, IOException {
        StringBuffer responseData_listCountry = new StringBuffer();
        responseData_listCountry.append("{\"content\": [{\"name\":\"Afghanistan\",\"alpha3Code\":\"AFG\"},");
        responseData_listCountry.append("{\"name\":\"Åland Islands\",\"alpha3Code\":\"ALA\"},");
        responseData_listCountry.append("{\"name\":\"Albania\",\"alpha3Code\":\"ALB\"},");
        responseData_listCountry.append("{\"name\":\"Algeria\",\"alpha3Code\":\"DZA\"}],");
        responseData_listCountry.append("\"status\":\"400\"}");

        when(httpRequestService.getReq(anyString(), eq(null))).thenReturn(new JSONObject(responseData_listCountry.toString()));

        assertThat(countryService.getAllCountries()).isNull();

        verify(httpRequestService, times(1)).getReq(anyString(), eq(null));
    }
}

