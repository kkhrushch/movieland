package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.MovieDao;
import com.khrushch.movieland.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private MovieDao movieDao;

    public List<Movie> getAll() {
        return movieDao.getAll();
    }

    @Autowired
    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }
}
