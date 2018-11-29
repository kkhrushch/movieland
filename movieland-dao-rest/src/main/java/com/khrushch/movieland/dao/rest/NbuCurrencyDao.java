package com.khrushch.movieland.dao.rest;

import com.khrushch.movieland.dao.CurrencyDao;
import com.khrushch.movieland.dao.rest.mapper.CurrencyRateMapper;
import com.khrushch.movieland.model.CurrencyCode;
import com.khrushch.movieland.model.CurrencyRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Repository
public class NbuCurrencyDao implements CurrencyDao {
    private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static final CurrencyRateMapper CURRENCY_RATE_MAPPER = new CurrencyRateMapper();

    //visibility for tests
    @Value("${currency.api.url}")
    String url;
    private RestTemplate restTemplate;
    private Map<CurrencyCode, Double> cachedRates;

    public double getRate(CurrencyCode toCurrency) {
        double rate = 0;
        readWriteLock.readLock().lock();
        rate = cachedRates.get(toCurrency);
        readWriteLock.readLock().unlock();

        return rate;
    }

    @Scheduled(cron = "${cache.refresh.schedule}")
    @PostConstruct
    void refreshCache() throws IOException {
        readWriteLock.writeLock().lock();

        String currencyRatesJson = restTemplate.getForObject(url, String.class);
        List<CurrencyRate> rates = CURRENCY_RATE_MAPPER.map(currencyRatesJson);
        cachedRates = rates.stream()
                .collect(Collectors.toMap(CurrencyRate::getCurrencyCode, CurrencyRate::getRate));

        readWriteLock.writeLock().unlock();
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

}
