package com.khrushch.movieland.rest.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khrushch.movieland.dao.jdbc.JdbcMovieDao;
import com.khrushch.movieland.model.Movie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.ArraySizeComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
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
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContext.xml", "file:src/main/webapp/WEB-INF/rest-v1-servlet.xml"})
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
        JdbcTemplate mockJdbcTemplate = mock(JdbcTemplate.class);
        when(mockJdbcTemplate.query(JdbcMovieDao.SELECT_ALL_MOVIES_SQL, JdbcMovieDao.movieRowMapper)).thenReturn(getTestMovies());

        JdbcMovieDao jdbcMovieDao = wac.getBean(JdbcMovieDao.class);
        jdbcMovieDao.setJdbcTemplate(mockJdbcTemplate);

        MvcResult mvcResult = mockMvc.perform(get("/movie"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        String actualJson = mvcResult.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String expectedJson = mapper.writeValueAsString(getTestMovies());

        JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.LENIENT);
        verify(mockJdbcTemplate, times(1)).query(JdbcMovieDao.SELECT_ALL_MOVIES_SQL, JdbcMovieDao.movieRowMapper);
    }

    @Test
    public void testGetRandomMovies() throws Exception {
        List<Movie> mockMovies = Stream.generate(Movie::new)
                .limit(20)
                .collect(Collectors.toList());
        JdbcTemplate mockJdbcTemplate = mock(JdbcTemplate.class);
        when(mockJdbcTemplate.query(JdbcMovieDao.SELECT_ALL_MOVIES_SQL, JdbcMovieDao.movieRowMapper)).thenReturn(mockMovies);

        JdbcMovieDao jdbcMovieDao = wac.getBean(JdbcMovieDao.class);
        jdbcMovieDao.setJdbcTemplate(mockJdbcTemplate);

        MvcResult mvcResult = mockMvc.perform(get("/movie/random"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        String actualJson = mvcResult.getResponse().getContentAsString();

        JSONAssert.assertEquals("[3]", actualJson, new ArraySizeComparator(JSONCompareMode.LENIENT));
        verify(mockJdbcTemplate, times(1)).query(JdbcMovieDao.SELECT_ALL_MOVIES_SQL, JdbcMovieDao.movieRowMapper);
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

        return new ArrayList<>(Arrays.asList(first, second));
    }

}