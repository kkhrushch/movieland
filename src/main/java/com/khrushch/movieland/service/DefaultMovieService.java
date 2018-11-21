package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.MovieDao;
import com.khrushch.movieland.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DefaultMovieService implements MovieService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private MovieDao movieDao;

    @Override
    public List<Movie> getAll(Map<String,String> requestParams) {
        List<Movie> movies = movieDao.getAll(getSortingParams(requestParams));
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
    public List<Movie> getByGenreId(long genreId, Map<String,String> requestParams) {
        List<Movie> movies = movieDao.getByGenreId(genreId, requestParams);
        logger.debug("Fetched movies by genreId {}: {}", genreId, movies);
        return movies;
    }

    Map<String,String> getSortingParams(Map<String,String> requestParams){
        Map<String,List<String>> allowedSortingParams = new HashMap<>();
        allowedSortingParams.put("rating", Arrays.asList("desc"));
        allowedSortingParams.put("price", Arrays.asList("desc", "asc"));

        return requestParams.entrySet().stream()
                .filter(e -> e.getKey().equalsIgnoreCase("price") || e.getKey().equalsIgnoreCase("rating"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Autowired
    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }
}
