package com.khrushch.movieland.dao.jdbc.query;

import com.khrushch.movieland.model.request.QueryParam;
import com.khrushch.movieland.model.request.SortingParam;

import java.util.List;
import java.util.stream.Collectors;

public class QueryBuilder {
    public static String build(String query, QueryParam queryParam) {
        return addOrderBy(query, queryParam.getSortingParams());
    }

    static String addOrderBy(String query, List<SortingParam> sortingParams) {
        if (sortingParams != null && !sortingParams.isEmpty()) {
            query = wrapWithSelect(query) + " ORDER BY " + sortingParams.stream()
                    .map(SortingParam::toString)
                    .collect(Collectors.joining(","));
        }
        return query;
    }

    private static String wrapWithSelect(String innerQuery) {
        return "SELECT * FROM (" + innerQuery + ")";
    }
}
