package com.khrushch.movieland.service.mapper;

import com.khrushch.movieland.model.CurrencyCode;
import com.khrushch.movieland.model.CurrencyRate;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class CurrencyRateMapperTest {

    @Test
    public void testMap() throws IOException {
        CurrencyRate expectedRateUsd = new CurrencyRate(CurrencyCode.USD, 1 / 28.5);
        CurrencyRate expectedRateEuro = new CurrencyRate(CurrencyCode.EUR, 1 / 31.5);

        String currencyJson = "[{\"r030\":840,\"txt\":\"Долар США\",\"rate\":28.5,\"cc\":\"USD\",\"exchangedate\":\"29.11.2018\"}," +
                "{\"r030\":978,\"txt\":\"Євро\",\"rate\":31.5,\"cc\":\"EUR\",\"exchangedate\":\"30.11.2018\"}]";

        CurrencyRateMapper mapper = new CurrencyRateMapper();
        List<CurrencyRate> actualRates = mapper.map(currencyJson);
        CurrencyRate actualRateUsd = actualRates.stream().filter(r -> r.getCurrencyCode() == CurrencyCode.USD).findAny().get();
        CurrencyRate actualRateEur = actualRates.stream().filter(r -> r.getCurrencyCode() == CurrencyCode.EUR).findAny().get();

        assertEquals(expectedRateUsd.getCurrencyCode(), actualRateUsd.getCurrencyCode());
        assertEquals(expectedRateUsd.getRate(), actualRateUsd.getRate(), 0.001);

        assertEquals(expectedRateEuro.getCurrencyCode(), actualRateEur.getCurrencyCode());
        assertEquals(expectedRateEuro.getRate(), actualRateEur.getRate(), 0.001);
    }
}