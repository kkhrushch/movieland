package com.khrushch.movieland.dao.jdbc.query;

public class MovieStatement {
    // visibility for tests
    public static final String SELECT_ALL_MOVIES_SQL = "SELECT id, name, original_name, year, rating, price, poster_url FROM movie";
    public static final String SELECT_RANDOM_MOVIES_SQL = "SELECT id, name, original_name, year, rating, price, poster_url FROM movie ORDER BY random() LIMIT 3";
    public static final String SELECT_MOVIES_BY_GENRE_ID = "" +
            "SELECT m.id, m.name, m.original_name, m.year, m.rating, m.price, m.poster_url " +
            "  FROM movie          m " +
            "       JOIN " +
            "       movie_genre    mg " +
            "         ON m.id      = mg.movie_id " +
            " WHERE mg.genre_id  = ?";

}
