package com.khrushch.movieland.dao;

import com.khrushch.movieland.model.CurrencyCode;

public interface CurrencyDao {

    double getRate(CurrencyCode toCurrency);
}
