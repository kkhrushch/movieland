package com.khrushch.movieland.common;

import java.util.Map;

public class MovieQueryParam {
    private Map<String, SortingOrder> sortingParams;

    public MovieQueryParam(Map<String, String> requestParams) {
        validateParams(requestParams);
        requestParams.
    }

    void validateParams(Map<String, String> requestParams) {
        requestParams.keySet().con
    }

}
