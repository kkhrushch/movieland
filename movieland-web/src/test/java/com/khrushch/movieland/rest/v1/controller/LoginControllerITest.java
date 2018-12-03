package com.khrushch.movieland.rest.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khrushch.movieland.dto.UserCredentialsDto;
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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml", "file:src/main/webapp/WEB-INF/rest-v1-servlet.xml"})
@WebAppConfiguration
public class LoginControllerITest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testDoLoginSuccess() throws Exception {
        String requestCredentialsJson = "{\"email\":\"ronald.reynolds66@example.com\", \"password\":\"paco\"}";

        MvcResult mvcResult = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestCredentialsJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        String responseCredentialsJson = mvcResult.getResponse().getContentAsString();

        UserCredentialsDto expectedCredentials = new UserCredentialsDto("UUID12345", "Рональд Рейнольдс");
        UserCredentialsDto actualCredentials = OBJECT_MAPPER.readValue(responseCredentialsJson, UserCredentialsDto.class);

        assertEquals(expectedCredentials.getNickname(), actualCredentials.getNickname());
        assertNotNull(actualCredentials.getUuid());
        assertNull(actualCredentials.getEmail());
        assertNull(actualCredentials.getPassword());

    }

    @Test
    public void testDoLoginWithInvalidCredentials() throws Exception {
        String requestCredentialsJson = "{\"email\":\"ronald.reynolds66@example.com\", \"password\":\"pacoZZZ\"}";

        MvcResult mvcResult = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestCredentialsJson))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseCredentialsJson = mvcResult.getResponse().getContentAsString();

        assertEquals("", responseCredentialsJson);

    }

    @Test
    public void testDoLoginSuccessfullyAndLogoutSuccessfully() throws Exception {
        // Login
        String loginRequestCredentialsJson = "{\"email\":\"ronald.reynolds66@example.com\", \"password\":\"paco\"}";

        MvcResult mvcResultLogin = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestCredentialsJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        String loginResponseCredentialsJson = mvcResultLogin.getResponse().getContentAsString();

        UserCredentialsDto expectedLoginCredentials = new UserCredentialsDto("UUID12345", "Рональд Рейнольдс");
        UserCredentialsDto actualLoginCredentials = OBJECT_MAPPER.readValue(loginResponseCredentialsJson, UserCredentialsDto.class);

        // Logout
        MvcResult mvcResultLogout = mockMvc.perform(delete("/logout")
                .header("uuid", actualLoginCredentials.getUuid()))
                .andExpect(status().isOk())
                .andReturn();

        String logoutResponseCredentialsJson = mvcResultLogout.getResponse().getContentAsString();

        assertEquals(expectedLoginCredentials.getNickname(), actualLoginCredentials.getNickname());
        assertNotNull(actualLoginCredentials.getUuid());
        assertNull(actualLoginCredentials.getEmail());
        assertNull(actualLoginCredentials.getPassword());

        assertEquals("", logoutResponseCredentialsJson);

    }

    @Test
    public void testDoLogoutWithInvalidUuid() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/login")
                .header("uuid", "UUID12345"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseCredentialsJson = mvcResult.getResponse().getContentAsString();

        assertEquals("", responseCredentialsJson);

    }
}