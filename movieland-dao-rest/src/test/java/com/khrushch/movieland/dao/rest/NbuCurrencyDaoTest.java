package com.khrushch.movieland.dao.rest;

import com.khrushch.movieland.model.CurrencyCode;
import com.khrushch.movieland.model.CurrencyRate;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NbuCurrencyDaoTest {

    @Test
    public void testGetRate() {
        RestTemplate mockRestTemplate = mock(RestTemplate.class);

        ResponseEntity<List<CurrencyRate>> mockResponseEntity = mock(ResponseEntity.class);
        List<CurrencyRate> currencyRates = Arrays.asList(new CurrencyRate(CurrencyCode.USD, 28.5));
        when(mockResponseEntity.getBody()).thenReturn(currencyRates);

        when(mockRestTemplate.exchange(notNull(String.class), any(), any(), any(ParameterizedTypeReference.class))).thenReturn(mockResponseEntity);

        NbuCurrencyDao nbuCurrencyDao = new NbuCurrencyDao();
        nbuCurrencyDao.url = "https://nbu.com";
        nbuCurrencyDao.setRestTemplate(mockRestTemplate);

        double actualRate = nbuCurrencyDao.getRate(CurrencyCode.USD);

        assertEquals(1/28.5, actualRate, 0.001);
    }
}