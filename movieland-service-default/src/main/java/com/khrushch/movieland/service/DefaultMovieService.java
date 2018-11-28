package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.MovieDao;
import com.khrushch.movieland.model.CurrencyCode;
import com.khrushch.movieland.model.Movie;
import com.khrushch.movieland.model.request.QueryParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultMovieService implements MovieService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private MovieDao movieDao;
    private GenreService genreService;
    private CountryService countryService;
    private ReviewService reviewService;
    private CurrencyService currencyService;

    @Override
    public List<Movie> getAll(QueryParam queryParam) {
        List<Movie> movies = movieDao.getAll(queryParam);
        logger.debug("Fetched movies: {}", movies);
        return movies;
    }

    @Override
    public List<Movie> getRandom() {
        List<Movie> movies = movieDao.getRandom();
        logger.debug("Fetched random movies: {}", movies);
        return movies;
    }

    @Override
    public List<Movie> getByGenreId(long genreId, QueryParam queryParam) {
        List<Movie> movies = movieDao.getByGenreId(genreId, queryParam);
        logger.debug("Fetched movies by genreId {}: {}", genreId, movies);
        return movies;
    }

    @Override
    public Movie getById(long id) {
        Movie movie = movieDao.getById(id);
        genreService.enrich(movie);
        countryService.enrich(movie);
        reviewService.enrich(movie);

        logger.debug("Fetched by id: {}", movie);
        return movie;
    }

    @Override
    public Movie getById(long id, QueryParam queryParam) {
        Movie movie = getById(id);

        double priceForCurrency = currencyService.convert(movie.getPrice(), queryParam.getCurrency());
        movie.setPrice(priceForCurrency);

        logger.debug("Fetched by id: {}, with price currency: ()", movie, queryParam.getCurrency());
        return movie;
    }

    @Autowired
    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

    @Autowired
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }
}
