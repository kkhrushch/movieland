package com.khrushch.movieland.model.request;

import com.khrushch.movieland.model.CurrencyCode;

import java.util.List;

public interface QueryParam {
    List<SortingParam> getSortingParams();

    CurrencyCode getCurrency();
}
