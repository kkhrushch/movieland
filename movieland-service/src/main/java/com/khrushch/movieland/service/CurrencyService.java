package com.khrushch.movieland.service;

import com.khrushch.movieland.model.CurrencyCode;

public interface CurrencyService {
    double convert(double price, CurrencyCode toCurrency);
}
