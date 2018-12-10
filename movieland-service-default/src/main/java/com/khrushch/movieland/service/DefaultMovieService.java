package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.MovieDao;
import com.khrushch.movieland.model.Movie;
import com.khrushch.movieland.model.request.QueryParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class DefaultMovieService implements MovieService {
    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(3, 20, 20L, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final long taskExecutionTimeoutSec = 5;

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
    public Movie getById(long id, QueryParam queryParam) {
        Movie movie = movieDao.getById(id);

        List<Runnable> runnableTasks = new ArrayList<>();
        runnableTasks.add(() -> genreService.enrich(movie));
        runnableTasks.add(() -> countryService.enrich(movie));
        runnableTasks.add(() -> reviewService.enrich(movie));
        runnableTasks.add(() -> {
            double priceForCurrency = currencyService.convert(movie.getPrice(), queryParam.getCurrency());
            movie.setPrice(priceForCurrency);
        });

        List<Callable<Object>> callableTasks = runnableTasks.stream().map(Executors::callable).collect(Collectors.toList());

        List<Future<Object>> runningTasks = null;
        LocalDateTime tasksExecutionDeadline = LocalDateTime.now().plusSeconds(taskExecutionTimeoutSec);
        try {
            runningTasks = EXECUTOR_SERVICE.invokeAll(callableTasks);

            for (Future<Object> task : runningTasks) {
                long remainingTimeoutMillis = ChronoUnit.MILLIS.between(LocalDateTime.now(), tasksExecutionDeadline);
                if (remainingTimeoutMillis > 0) {
                    task.get(remainingTimeoutMillis, TimeUnit.MILLISECONDS);
                } else{
                    logger.debug("Failed to enrich movie, id: {}", id);
                    throw new RuntimeException("Task execution dealine exceeded for movie id: " + id);
                }
            }

        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            logger.debug("Failed to enrich movie. id: {}", id, e);
            throw new RuntimeException("Failed to enrich movie, id: " + id, e);
        } finally {
            closeAll(runningTasks);
        }

        logger.debug("Fetched by id: {}, with price currency: {}", movie, queryParam.getCurrency());
        return movie;
    }

    private void closeAll(List<Future<Object>> tasks){
        for (Future<Object> task : tasks){
            if(!task.isDone() && !task.isCancelled()){
                try{
                    task.cancel(true);
                } catch (Exception e){
                    logger.debug("Failed to cancel task", e);
                }
            }
        }
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
