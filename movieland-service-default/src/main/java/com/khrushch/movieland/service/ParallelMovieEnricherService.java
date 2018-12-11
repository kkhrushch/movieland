package com.khrushch.movieland.service;

import com.khrushch.movieland.model.Movie;
import com.khrushch.movieland.model.request.QueryParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class ParallelMovieEnricherService implements MovieEnricherService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private long taskExecutionTimeoutSec;

    private ExecutorService executorService;
    private GenreService genreService;
    private CountryService countryService;
    private ReviewService reviewService;
    private CurrencyService currencyService;

    @Override
    public void enrich(Movie movie, QueryParam queryParam) {
        List<Runnable> runnableTasks = new ArrayList<>();
        runnableTasks.add(() -> genreService.enrich(movie));
        runnableTasks.add(() -> countryService.enrich(movie));
        runnableTasks.add(() -> reviewService.enrich(movie));
        runnableTasks.add(() -> {
            double priceForCurrency = currencyService.convert(movie.getPrice(), queryParam.getCurrency());
            movie.setPrice(priceForCurrency);
        });

        List<Callable<Object>> callableTasks = runnableTasks.stream().map(Executors::callable).collect(Collectors.toList());

        try {
            executorService.invokeAll(callableTasks, taskExecutionTimeoutSec, TimeUnit.SECONDS);
        } catch (InterruptedException  e) {
            logger.debug("Failed to enrich movie. id: {}", movie.getId(), e);
            throw new RuntimeException("Failed to enrich movie, id: " + movie.getId(), e);
        }

    }

    @Value("${task.execution.timeout.sec}")
    public void setTaskExecutionTimeoutSec(long taskExecutionTimeoutSec) {
        this.taskExecutionTimeoutSec = taskExecutionTimeoutSec;
    }

    @Autowired
    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
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
