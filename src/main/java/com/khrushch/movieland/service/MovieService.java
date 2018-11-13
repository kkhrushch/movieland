package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.MovieDao;
import com.khrushch.movieland.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private static final int NUMBER_OF_RANDOM_MOVIES = 3;
    private static final Random random = new Random();

    private MovieDao movieDao;

    public List<Movie> getAll() {
        return movieDao.getAll();
    }

    public List<Movie> getRandomMovies() {
        List<Movie> allMovies = movieDao.getAll();

        return random.ints(NUMBER_OF_RANDOM_MOVIES, 0, allMovies.size())
                .mapToObj(allMovies::get)
                .collect(Collectors.toList());
    }

    @Autowired
    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }
}
