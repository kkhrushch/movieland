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

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
@PropertySource("classpath:app-dao-jdbc.properties")
public class CachedGenreDao implements GenreDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private volatile List<Genre> genreCache = new ArrayList<>();
    private GenreDao genreDao;

    public List<Genre> getAll() {
        List<Genre> genres = new ArrayList<>(genreCache);
        logger.debug("Returning genres from cache: {}", genres);
        return genres;
    }

    public List<Genre> getByMovieId(long movieId) {
        List<Genre> genres = genreDao.getByMovieId(movieId);
        logger.debug("Returning genres by movieId {} bypassing cache: {}", movieId, genres);
        return genres;
    }

    @Scheduled(fixedDelayString = "${genre.cache.refresh.interval.millis}", initialDelayString = "${genre.cache.refresh.interval.millis}")
    @PostConstruct
    void refreshCache() {
        genreCache = genreDao.getAll();
        logger.debug("Refreshed cache; genres: {}", genreCache);
    }

    @Autowired
    public void setGenreDao(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

}
