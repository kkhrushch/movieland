package com.khrushch.movieland.service;

import com.khrushch.movieland.model.CurrencyCode;
import com.khrushch.movieland.model.Movie;
import com.khrushch.movieland.model.request.QueryParam;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ParallelMovieEnricherServiceTest {

    @Test
    public void enrich() {
        QueryParam mockQueryParam = mock(QueryParam.class);
        when(mockQueryParam.getCurrency()).thenReturn(CurrencyCode.USD);

        Movie movie = getTestMovie();
        double initialPrice = movie.getPrice();
        double convertedPrice = 999.9;

        GenreService mockGenreService = mock(GenreService.class);
        CountryService mockCountryService = mock(CountryService.class);
        ReviewService mockReviewService = mock(ReviewService.class);
        ExecutorService executorService = Executors.newCachedThreadPool();

        CurrencyService mockCurrencyService = mock(CurrencyService.class);
        when(mockCurrencyService.convert(initialPrice, CurrencyCode.USD)).thenReturn(convertedPrice);

        ParallelMovieEnricherService movieEnricherService = new ParallelMovieEnricherService();
        movieEnricherService.setTaskExecutionTimeoutSec(5);
        movieEnricherService.setExecutorService(executorService);
        movieEnricherService.setGenreService(mockGenreService);
        movieEnricherService.setCountryService(mockCountryService);
        movieEnricherService.setReviewService(mockReviewService);
        movieEnricherService.setCurrencyService(mockCurrencyService);

        movieEnricherService.enrich(movie, mockQueryParam);

        verify(mockGenreService, times(1)).enrich(movie);
        verify(mockCountryService, times(1)).enrich(movie);
        verify(mockReviewService, times(1)).enrich(movie);
        verify(mockCurrencyService, times(1)).convert(initialPrice, CurrencyCode.USD);
        verify(mockQueryParam, atLeastOnce()).getCurrency();
        assertEquals(movie.getPrice(), convertedPrice, 0.001);
        movie.setPrice(initialPrice);
        assertEquals(getTestMovie(), movie);
    }

    private Movie getTestMovie() {
        Movie first = new Movie();
        first.setId(1);
        first.setRussianName("Список Шиндлера");
        first.setNativeName("Schindler's List");
        first.setYearOfRelease(1993);
        first.setRating(8.7);
        first.setPrice(150.5);
        first.setPicturePath("https://images-na.ssl-images-amazon.com/images/M/MV5BNDE4OTMxMTctNmRhYy00NWE2LTg3YzItYTk3M2UwOTU5Njg4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SX140_CR0,0,140,209_.jpg");

        return first;
    }
}