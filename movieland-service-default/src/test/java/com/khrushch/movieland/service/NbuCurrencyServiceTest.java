package com.khrushch.movieland.service;

import com.khrushch.movieland.model.CurrencyCode;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NbuCurrencyServiceTest {

    @Test
    public void testGetRate() throws IOException {
        RestTemplate mockRestTemplate = mock(RestTemplate.class);

        String currencyJson = "[{\"r030\":840,\"txt\":\"Долар США\",\"rate\":28.5,\"cc\":\"USD\",\"exchangedate\":\"29.11.2018\"}]";

        String url = "https://nbu.com";
        when(mockRestTemplate.getForObject(url, String.class)).thenReturn(currencyJson);

        NbuCurrencyService nbuCurrencyService = new NbuCurrencyService("UAH");
        nbuCurrencyService.url = "https://nbu.com";
        nbuCurrencyService.setRestTemplate(mockRestTemplate);
        nbuCurrencyService.refreshCache();

        double price = 205;
        double actualPrice = nbuCurrencyService.convert(price, CurrencyCode.USD);

        assertEquals(price / 28.5, actualPrice, 0.001);
    }
}