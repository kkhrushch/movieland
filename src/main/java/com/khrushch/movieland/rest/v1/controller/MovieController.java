package com.khrushch.movieland.rest.v1.controller;

import com.khrushch.movieland.model.Movie;
import com.khrushch.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieController {
    private MovieService movieService;

    @GetMapping("/movie")
    public List<Movie> getAll() {
        return movieService.getAll();
    }

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }
}
