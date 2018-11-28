package com.khrushch.movieland.model.request;

import com.khrushch.movieland.model.CurrencyCode;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieQueryParam implements QueryParam {
    private static final List<SortingParam> ALLOWED_SORTING_PARAMS = Arrays.asList(
            new SortingParam("rating", SortingOrder.DESC),
            new SortingParam("price", SortingOrder.ASC),
            new SortingParam("price", SortingOrder.DESC)
    );
    private static final CurrencyCode DEFAULT_CURRENCY = CurrencyCode.UAH;

    private List<SortingParam> sortingParams;
    private CurrencyCode currency;

    public MovieQueryParam(Map<String, String> requestParams) {
        extractSortingParams(requestParams);
        extractCurrency(requestParams);
    }

    void extractSortingParams(Map<String, String> requestParams) {
        List<String> sortingFields = ALLOWED_SORTING_PARAMS.stream()
                .map(SortingParam::getColumnName)
                .collect(Collectors.toList());

        sortingParams = requestParams.entrySet().stream()
                .filter(e -> sortingFields.contains(e.getKey().trim().toLowerCase()))
                .map(e -> new SortingParam(e.getKey(), SortingOrder.forName(e.getValue())))
                .collect(Collectors.toList());

        if (!ALLOWED_SORTING_PARAMS.containsAll(sortingParams)) {
            throw new IllegalArgumentException("Only allowed sorting parameters: " + ALLOWED_SORTING_PARAMS);
        }
    }

    void extractCurrency(Map<String, String> requestParams) {
        String currencyCodeString = requestParams.get("currency");

        if (currencyCodeString == null) {
            currency = DEFAULT_CURRENCY;
        } else {
            currency = CurrencyCode.forName(currencyCodeString);
        }
    }

    public List<SortingParam> getSortingParams() {
        return sortingParams;
    }

    public CurrencyCode getCurrency() {
        return currency;
    }
}
