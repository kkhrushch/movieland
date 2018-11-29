package com.khrushch.movieland.dao.rest;

import com.khrushch.movieland.model.CurrencyCode;
import com.khrushch.movieland.model.CurrencyRate;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NbuCurrencyDaoTest {

    @Test
    public void testGetRate() throws IOException {
        RestTemplate mockRestTemplate = mock(RestTemplate.class);

        String currencyJson = "[{\"r030\":840,\"txt\":\"Долар США\",\"rate\":28.269049,\"cc\":\"USD\",\"exchangedate\":\"29.11.2018\"}]";

        String url = "https://nbu.com";
        when(mockRestTemplate.getForObject(url, String.class)).thenReturn(currencyJson);

        NbuCurrencyDao nbuCurrencyDao = new NbuCurrencyDao();
        nbuCurrencyDao.url = "https://nbu.com";
        nbuCurrencyDao.setRestTemplate(mockRestTemplate);
        nbuCurrencyDao.refreshCache();

        double actualRate = nbuCurrencyDao.getRate(CurrencyCode.USD);

        assertEquals(1/28.5, actualRate, 0.001);
    }
}