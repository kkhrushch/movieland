package com.khrushch.movieland.dao.jdbc.query;

import java.util.Map;

import static java.util.stream.Collectors.joining;

public class MovieQuery {
    // visibility for tests
    static final String SELECT_ALL_MOVIES_SQL = "SELECT id, name, original_name, year, rating, price, poster_url FROM movie";
    static final String SELECT_RANDOM_MOVIES_SQL = "SELECT id, name, original_name, year, rating, price, poster_url FROM movie ORDER BY random() LIMIT 3";
    static final String SELECT_MOVIES_BY_GENRE_ID = "" +
            "SELECT m.id, m.name, m.original_name, m.year, m.rating, m.price, m.poster_url " +
            "  FROM movie          m " +
            "       JOIN " +
            "       movie_genre    mg " +
            "         ON m.id      = mg.movie_id " +
            " WHERE mg.genre_id  = ?";

    public static String getSelectAllMoviesSql(Map<String, String> sortingParams) {
        return addOrderByClause(SELECT_ALL_MOVIES_SQL, sortingParams);
    }

    public static String getSelectRandomMoviesSql() {
        return SELECT_RANDOM_MOVIES_SQL;
    }

    public static String getSelectMoviesByGenreId(Map<String, String> sortingParams) {
        return addOrderByClause(SELECT_MOVIES_BY_GENRE_ID, sortingParams);
    }

    static String addOrderByClause(String query, Map<String, String> sortingParams) {
        if (!sortingParams.isEmpty()) {
            query += " ORDER BY " + sortingParams.entrySet().stream()
                    .map(e -> e.getKey() + " " + e.getValue())
                    .collect(joining(","));
        }
        return query;
    }
}
