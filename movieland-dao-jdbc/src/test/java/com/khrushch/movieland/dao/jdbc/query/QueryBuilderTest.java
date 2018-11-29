package com.khrushch.movieland.dao.jdbc.query;

import com.khrushch.movieland.model.request.MovieQueryParam;
import com.khrushch.movieland.model.request.QueryParam;
import com.khrushch.movieland.model.request.SortingOrder;
import com.khrushch.movieland.model.request.SortingParam;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class QueryBuilderTest {

    @Test
    public void testBuild() {
        String initialQuery = "INNER_QUERY";

        HashMap<String,String> requestParams = new HashMap<>();
        requestParams.put("price", "ASC");
        requestParams.put("rating", "DESC");
        QueryParam queryParam = new MovieQueryParam(requestParams);

        String actualQuery = QueryBuilder.build(initialQuery, queryParam);
        String expectedQuery = "SELECT * FROM (" + initialQuery + ") s  ORDER BY price ASC,rating DESC";

        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void testAddOrderBy() {
        String initialQuery = "INNER_QUERY";

        List<SortingParam> sortingParams = Arrays.asList(
                new SortingParam("price", SortingOrder.forName("ASC")),
                new SortingParam("rating", SortingOrder.forName("DESC"))
        );

        String actualQuery = QueryBuilder.addOrderBy(initialQuery, sortingParams);
        String expectedQuery = "SELECT * FROM (" + initialQuery + ") s  ORDER BY price ASC,rating DESC";

        assertEquals(expectedQuery, actualQuery);
    }
}