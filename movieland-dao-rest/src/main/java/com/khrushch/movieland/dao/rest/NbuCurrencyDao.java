package com.khrushch.movieland.dao.rest;

import com.khrushch.movieland.dao.CurrencyDao;
import com.khrushch.movieland.model.CurrencyCode;
import com.khrushch.movieland.model.CurrencyRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Repository
public class NbuCurrencyDao implements CurrencyDao {
    private static final ParameterizedTypeReference<List<CurrencyRate>> PARAMETERIZED_TYPE_REFERENCE = new ParameterizedTypeReference<List<CurrencyRate>>(){};

    //visibility for tests
    @Value("${currency.api.url}")
    String url;

    private RestTemplate restTemplate;

    public double getRate(CurrencyCode toCurrency) {
        String uri = buildUri(toCurrency);

        ResponseEntity<List<CurrencyRate>> currencyRates = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                PARAMETERIZED_TYPE_REFERENCE);

        double rate = 1D / currencyRates.getBody().get(0).getRate();
        return rate;
    }

    private String buildUri(CurrencyCode toCurrency) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("json")
                .queryParam("valcode", toCurrency);
        String uri = builder.toUriString();

        return uri;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

}
