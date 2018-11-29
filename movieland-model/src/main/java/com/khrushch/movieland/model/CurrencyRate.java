package com.khrushch.movieland.model;

public class CurrencyRate {
    CurrencyCode currencyCode;
    double rate;

    public CurrencyRate(CurrencyCode currencyCode, double rate) {
        this.currencyCode = currencyCode;
        this.rate = rate;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public double getRate() {
        return rate;
    }
}
