package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.GenreDao;
import com.khrushch.movieland.model.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreCacheService {
    private static final Logger logger = LoggerFactory.getLogger(GenreCacheService.class);
    private static final int CACHE_REFRESH_INTERVAL_MS = 4 * 60 * 60 * 1000;

    private volatile List<Genre> genreCache = new ArrayList<>();
    private GenreDao genreDao;

    public List<Genre> getAll() {
        ArrayList<Genre> genres = new ArrayList<>(genreCache);
        logger.debug("Returning genres from cache: {}", genres);
        return genres;
    }

    @Scheduled(fixedDelay = CACHE_REFRESH_INTERVAL_MS)
    private void refreshCache() {
        genreCache = genreDao.getAll();
        logger.debug("Refreshed cache; genres: {}", genreCache);
    }

    @Autowired
    public void setGenreDao(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

}
