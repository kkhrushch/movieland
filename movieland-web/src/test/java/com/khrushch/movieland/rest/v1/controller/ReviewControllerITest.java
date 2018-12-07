package com.khrushch.movieland.rest.v1.controller;

import com.khrushch.movieland.dto.UserCredentialsDto;
import com.khrushch.movieland.model.Movie;
import com.khrushch.movieland.model.Review;
import com.khrushch.movieland.model.User;
import com.khrushch.movieland.rest.v1.holder.CurrentUserHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml", "file:src/main/webapp/WEB-INF/rest-v1-servlet.xml"})
@WebAppConfiguration
public class ReviewControllerITest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testAddMovie() throws Exception {
        UserCredentialsDto requestCredentialsDto = new UserCredentialsDto();
        requestCredentialsDto.setEmail("ronald.reynolds66@example.com");
        requestCredentialsDto.setPassword("paco");

        User user = new User();
        user.setId(3);
        user.setRole("USER");
        CurrentUserHolder.setUser(user);

        LoginController loginController = wac.getBean(LoginController.class);
        UserCredentialsDto responseCredentials = loginController.doLogin(requestCredentialsDto);

        String addMovieJson = "{\"movieId\":1, \"text\":\"added review\"}";


        MvcResult mvcResult = mockMvc.perform(post("/review")
                .contentType(MediaType.APPLICATION_JSON)
                .header("uuid", responseCredentials.getUuid())
                .content(addMovieJson))
                .andExpect(status().isOk())
                .andReturn();

        MovieController movieController = wac.getBean(MovieController.class);
        Movie actualMovie = movieController.getById(1, new HashMap<>());

        boolean isAddedReviewExists = actualMovie.getReviews().stream()
                .anyMatch(r -> r.getText().equals("added review"));

        assertTrue(isAddedReviewExists);
    }
}