package com.khrushch.movieland.model;

public enum CurrencyCode {
    UAH,
    USD,
    EUR;

    public static CurrencyCode forName(String name) {
        for (CurrencyCode value : values()) {
            if (value.name().equalsIgnoreCase(name.trim())) {
                return value;
            }
        }
        throw new IllegalArgumentException("Cannot get SortingOrder for name: " + name);
    }

}
