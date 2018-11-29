package com.khrushch.movieland.model.request;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieQueryParam implements QueryParam {
    private static final List<SortingParam> allowedSortingParams = Arrays.asList(
            new SortingParam("rating", SortingOrder.DESC),
            new SortingParam("price", SortingOrder.ASC),
            new SortingParam("price", SortingOrder.DESC)
    );
    private List<SortingParam> sortingParams;

    public MovieQueryParam(Map<String, String> requestParams) {
        getSortingParams(requestParams);
    }

    public List<SortingParam> getSortingParams() {
        return sortingParams;
    }

    void getSortingParams(Map<String, String> requestParams) {
        sortingParams = requestParams.entrySet().stream()
                .map(e -> new SortingParam(e.getKey(), SortingOrder.forName(e.getValue())))
                .collect(Collectors.toList());

        if (!allowedSortingParams.containsAll(sortingParams)) {
            throw new IllegalArgumentException("Only allowed sorting parameters: " + allowedSortingParams);
        }
    }

}
