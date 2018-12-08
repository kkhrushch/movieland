package com.khrushch.movieland.rest.v1.controller;

import com.khrushch.movieland.model.Movie;
import com.khrushch.movieland.model.request.MovieQueryParam;
import com.khrushch.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class MovieController {
    private MovieService movieService;

    @GetMapping("/movie")
    public List<Movie> getAll(
            @RequestParam Map<String, String> requestParams) {
        return movieService.getAll(new MovieQueryParam(requestParams));
    }

    @GetMapping("/movie/random")
    public List<Movie> getRandom() {
        return movieService.getRandom();
    }

    @GetMapping("/movie/genre/{genreId}")
    public List<Movie> getByGenreId(@PathVariable long genreId, @RequestParam Map<String, String> requestParams) {
        return movieService.getByGenreId(genreId, new MovieQueryParam(requestParams));
    }

    @GetMapping("/movie/{id}")
    public Movie getById(@PathVariable long id, @RequestParam Map<String, String> requestParam) {
        return movieService.getById(id, new MovieQueryParam(requestParam));
    }

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }
}
