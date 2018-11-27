package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.CurrencyDao;
import com.khrushch.movieland.model.CurrencyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultCurrencyService implements CurrencyService {
    private static final CurrencyCode DEFAULT_CURRENCY = CurrencyCode.UAH;

    private CurrencyDao currencyDao;

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
