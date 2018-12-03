package com.khrushch.movieland.service;

import com.khrushch.movieland.model.CurrencyCode;
import com.khrushch.movieland.model.CurrencyRate;
import com.khrushch.movieland.service.mapper.CurrencyRateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NbuCurrencyService implements CurrencyService {
    private static final CurrencyRateMapper CURRENCY_RATE_MAPPER = new CurrencyRateMapper();
    private CurrencyCode defaultCurrency;

    //visibility for tests
    @Value("${currency.api.url}")
    String url;
    private RestTemplate restTemplate;
    private volatile Map<CurrencyCode, Double> cachedRates;

    public NbuCurrencyService(@Value("${currency.default}") String currencyCode) {
        defaultCurrency = CurrencyCode.forName(currencyCode);
    }

    @Override
    public double convert(double price, CurrencyCode toCurrency) {
        if (toCurrency == null || toCurrency == defaultCurrency) {
            return price;
        } else {
            return price * cachedRates.get(toCurrency);
        }
    }

    @Scheduled(cron = "${currency.cache.refresh.schedule}")
    @PostConstruct
    void refreshCache() throws IOException {
        String currencyRatesJson = restTemplate.getForObject(url, String.class);
        List<CurrencyRate> rates = CURRENCY_RATE_MAPPER.map(currencyRatesJson);
        cachedRates = rates.stream()
                .collect(Collectors.toMap(CurrencyRate::getCurrencyCode, CurrencyRate::getRate));
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

}
