package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.MovieDao;
import com.khrushch.movieland.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);
    private static final int NUMBER_OF_RANDOM_MOVIES = 3;
    private static final Random random = new Random();

    private MovieDao movieDao;

    public List<Movie> getAll() {
        List<Movie> movies = movieDao.getAll();
        logger.debug("Fetched movies: {}", movies);
        return movies;
    }

    public List<Movie> getRandomMovies() {
        List<Movie> allMovies = movieDao.getAll();

        List<Movie> movies = random.ints(NUMBER_OF_RANDOM_MOVIES, 0, allMovies.size())
                .mapToObj(allMovies::get)
                .collect(Collectors.toList());
        logger.debug("Fetched random movies: {}", movies);
        return movies;
    }

    @Autowired
    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }
}
