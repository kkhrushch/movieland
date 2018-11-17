package com.khrushch.movieland.dao.cached;

import com.khrushch.movieland.dao.GenreDao;
import com.khrushch.movieland.model.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
@PropertySource("classpath:app.properties")
public class CachedGenreDao implements GenreDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private volatile List<Genre> genreCache = new ArrayList<>();
    private GenreDao genreDao;

    public List<Genre> getAll() {
        ArrayList<Genre> genres = new ArrayList<>(genreCache);
        logger.debug("Returning genres from cache: {}", genres);
        return genres;
    }

    @Scheduled(fixedDelayString = "${genre.cache.refresh.interval.millis}", initialDelayString = "${genre.cache.refresh.interval.millis}")
    private void refreshCache() {
        genreCache = genreDao.getAll();
        logger.debug("Refreshed cache; genres: {}", genreCache);
    }

    @PostConstruct
    public void init() {
        refreshCache();
    }

    @Autowired
    public void setGenreDao(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

}
