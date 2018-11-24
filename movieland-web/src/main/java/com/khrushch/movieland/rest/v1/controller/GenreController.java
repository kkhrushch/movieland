package com.khrushch.movieland.rest.v1.controller;

import com.khrushch.movieland.model.Genre;
import com.khrushch.movieland.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GenreController {
    private GenreService genreService;

    @GetMapping("/genre")
    public List<Genre> getAll() {
        return genreService.getAll();
    }

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }
}
