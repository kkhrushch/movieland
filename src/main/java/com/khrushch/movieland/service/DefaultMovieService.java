package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.MovieDao;
import com.khrushch.movieland.model.Movie;
import com.khrushch.movieland.model.request.QueryParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultMovieService implements MovieService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private MovieDao movieDao;

    @Override
    public List<Movie> getAll(QueryParam queryParam) {
        List<Movie> movies = movieDao.getAll(queryParam);
        logger.debug("Fetched movies: {}", movies);
        return movies;
    }

    @Override
    public List<Movie> getRandom() {
        List<Movie> movies = movieDao.getRandom();
        logger.debug("Fetched random movies: {}", movies);
        return movies;
    }

    @Override
    public List<Movie> getByGenreId(long genreId, QueryParam queryParam) {
        List<Movie> movies = movieDao.getByGenreId(genreId, queryParam);
        logger.debug("Fetched movies by genreId {}: {}", genreId, movies);
        return movies;
    }

    @Autowired
    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }
}
