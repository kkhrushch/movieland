package com.khrushch.movieland.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrencyRate {
    CurrencyCode currencyCode;
    double rate;

    public CurrencyRate() {
    }

    public CurrencyRate(CurrencyCode currencyCode, double rate) {
        this.currencyCode = currencyCode;
        this.rate = rate;
    }

    @JsonProperty("cc")
    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public double getRate() {
        return rate;
    }
}
