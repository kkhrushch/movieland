package com.khrushch.movieland.rest.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khrushch.movieland.dao.CurrencyDao;
import com.khrushch.movieland.dao.MovieDao;
import com.khrushch.movieland.model.*;
import com.khrushch.movieland.service.DefaultCurrencyService;
import com.khrushch.movieland.service.DefaultMovieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml", "file:src/main/webapp/WEB-INF/rest-v1-servlet.xml"})
@WebAppConfiguration
public class MovieControllerITest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testGetAll() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/movie"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        String actualJson = mvcResult.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String expectedJson = mapper.writeValueAsString(getTestMovies());

        JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.LENIENT);
    }

    @Test
    public void testGetAllWithSorting() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/movie?rating=DESC&price=asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        String actualJson = mvcResult.getResponse().getContentAsString();

        List<Movie> testMoviesSorted = Arrays.asList(
                getTestMovies().get(2),
                getTestMovies().get(0),
                getTestMovies().get(3),
                getTestMovies().get(1)
        );

        ObjectMapper mapper = new ObjectMapper();
        String expectedJson = mapper.writeValueAsString(testMoviesSorted);

        JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.STRICT);
    }

    @Test
    @DirtiesContext
    public void testGetRandom() throws Exception {
        MovieDao mockMovieDao = mock(MovieDao.class);
        when(mockMovieDao.getRandom()).thenReturn(getTestMovies());

        DefaultMovieService defaultMovieService = wac.getBean(DefaultMovieService.class);
        defaultMovieService.setMovieDao(mockMovieDao);

        MvcResult mvcResult = mockMvc.perform(get("/movie/random"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        String actualJson = mvcResult.getResponse().getContentAsString();

        String expectedJson = new ObjectMapper().writeValueAsString(getTestMovies());

        JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.LENIENT);
    }

    @Test
    public void testGetByGenreId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/movie/genre/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        String actualJson = mvcResult.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        List<Movie> moviesWithGenreIdEqual1 = getTestMovies().stream()
                .filter(m -> Arrays.asList(1L, 2L, 4L).contains(m.getId()))
                .collect(Collectors.toList());
        String expectedJson = mapper.writeValueAsString(moviesWithGenreIdEqual1);

        JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.LENIENT);
    }

    @Test
    public void testGetById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/movie/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        String actualJson = mvcResult.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();

        Movie expectedMovie = getTestMovieWithAllFieldsSet();

        String expectedJson = mapper.writeValueAsString(expectedMovie);

        JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.LENIENT);
    }

    @Test
    @DirtiesContext
    public void testGetByIdWithCurrency() throws Exception {
        CurrencyDao mockCurrencyDao = mock(CurrencyDao.class);
        double usdRate = 1 / 28.5;
        when(mockCurrencyDao.getRate(CurrencyCode.USD)).thenReturn(usdRate);

        DefaultCurrencyService currencyService = wac.getBean(DefaultCurrencyService.class);
        currencyService.setCurrencyDao(mockCurrencyDao);

        MvcResult mvcResult = mockMvc.perform(get("/movie/1?currency=Usd"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        String actualJson = mvcResult.getResponse().getContentAsString();

        Movie expectedMovie = getTestMovieWithAllFieldsSet();
        expectedMovie.setPrice(expectedMovie.getPrice() * usdRate);

        Movie actualMovie = new ObjectMapper().readValue(actualJson, Movie.class);

        assertEquals(expectedMovie.getId(), actualMovie.getId());
        assertEquals(expectedMovie.getPrice(), actualMovie.getPrice(), 0.001);
        assertEquals(expectedMovie.getYearOfRelease(), actualMovie.getYearOfRelease());
        assertEquals(expectedMovie.getDescription(), actualMovie.getDescription());
        assertEquals(expectedMovie.getNativeName(), actualMovie.getNativeName());
        assertEquals(expectedMovie.getPicturePath(), actualMovie.getPicturePath());
        assertEquals(expectedMovie.getRating(), actualMovie.getRating(), 0.001);
    }

    private List<Movie> getTestMovies() {
        Movie first = new Movie();
        first.setId(1);
        first.setRussianName("Список Шиндлера");
        first.setNativeName("Schindler's List");
        first.setYearOfRelease(1993);
        first.setRating(8.7);
        first.setPrice(150.5);
        first.setPicturePath("https://images-na.ssl-images-amazon.com/images/M/MV5BNDE4OTMxMTctNmRhYy00NWE2LTg3YzItYTk3M2UwOTU5Njg4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SX140_CR0,0,140,209_.jpg");

        Movie second = new Movie();
        second.setId(2);
        second.setRussianName("Унесённые призраками");
        second.setNativeName("Sen to Chihiro no kamikakushi");
        second.setYearOfRelease(2001);
        second.setRating(8.6);
        second.setPrice(145.9);
        second.setPicturePath("https://images-na.ssl-images-amazon.com/images/M/MV5BOGJjNzZmMmUtMjljNC00ZjU5LWJiODQtZmEzZTU0MjBlNzgxL2ltYWdlXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1._SY209_CR0,0,140,209_.jpg");

        Movie third = new Movie();
        third.setId(3);
        third.setRussianName("Побег из Шоушенка");
        third.setNativeName("The Shawshank Redemption");
        third.setYearOfRelease(1994);
        third.setRating(8.9);
        third.setPrice(123.45);
        third.setPicturePath("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg");

        Movie fourth = new Movie();
        fourth.setId(4);
        fourth.setRussianName("Зеленая миля");
        fourth.setNativeName("The Green Mile");
        fourth.setYearOfRelease(1999);
        fourth.setRating(8.6);
        fourth.setPrice(134.67);
        fourth.setPicturePath("https://images-na.ssl-images-amazon.com/images/M/MV5BMTUxMzQyNjA5MF5BMl5BanBnXkFtZTYwOTU2NTY3._V1._SY209_CR0,0,140,209_.jpg");

        return Arrays.asList(first, second, third, fourth);
    }

    private Movie getTestMovieWithAllFieldsSet() {
        Movie movie = getTestMovies().get(0);
        movie.setCountries(getTestCountries());
        movie.setGenres(getTestGenres());
        movie.setReviews(getTestReviews());

        return movie;
    }

    private List<Country> getTestCountries() {
        return Arrays.asList(
                new Country(1, "США"),
                new Country(2, "Франция")
        );
    }

    private List<Genre> getTestGenres() {
        return new ArrayList<>(Arrays.asList(
                new Genre(1, "вестерн"),
                new Genre(2, "ужасы")
        ));
    }

    private List<Review> getTestReviews() {
        Review first = new Review();
        first.setId(1L);
        first.setUser(new User(1L, "a nickname"));
        first.setText("a review");

        Review second = new Review();
        second.setId(2L);
        second.setUser(new User(2L, "another nickname"));
        second.setText("another review");

        return Arrays.asList(first, second);
    }

}