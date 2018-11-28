package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.CurrencyDao;
import com.khrushch.movieland.model.CurrencyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:app-service-default.properties")
public class DefaultCurrencyService implements CurrencyService {
    private CurrencyCode DEFAULT_CURRENCY = CurrencyCode.forName(@Value(""));

    private CurrencyDao currencyDao;

    public DefaultCurrencyService() {
        @Value("")
    }

    @Override
    public double convert(double price, CurrencyCode toCurrency) {
        if (toCurrency == DEFAULT_CURRENCY) {
            return price;
        } else {
            return price * currencyDao.getRate(toCurrency);
        }
    }

    @Autowired
    public void setCurrencyDao(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }
}
