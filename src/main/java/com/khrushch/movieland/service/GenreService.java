package com.khrushch.movieland.service;

import com.khrushch.movieland.model.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private Logger logger = LoggerFactory.getLogger(GenreService.class);

    private GenreCacheService cacheService;

    public List<Genre> getAll() {
        List<Genre> genres = cacheService.getAll();
        logger.debug("Fetched genres: {}", genres);
        return genres;
    }

    @Autowired
    public void setCacheService(GenreCacheService cacheService) {
        this.cacheService = cacheService;
    }
}
