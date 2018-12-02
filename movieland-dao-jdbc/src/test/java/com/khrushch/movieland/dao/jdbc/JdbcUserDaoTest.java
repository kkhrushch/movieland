package com.khrushch.movieland.dao.jdbc;

import com.khrushch.movieland.model.User;
import com.khrushch.movieland.model.security.UserRole;
import org.junit.Test;
import org.mockito.Matchers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JdbcUserDaoTest {

    @Test
    public void testGetByEmail() {
        User expectedUser = new User();
        expectedUser.setId(0);
        expectedUser.setNickname("aNickname");
        expectedUser.setRole(new UserRole("aRole"));
        expectedUser.setPassword("aPassword");

        JdbcTemplate mockJdbcTemplate = mock(JdbcTemplate.class);
        when(mockJdbcTemplate.queryForObject(anyString(), Matchers.<RowMapper<User>>any(), anyString())).thenReturn(expectedUser);

        JdbcUserDao userDao = new JdbcUserDao();
        userDao.setJdbcTemplate(mockJdbcTemplate);

        User actualUser = userDao.getByEmail("abc");

        verify(mockJdbcTemplate).queryForObject(anyString(), Matchers.<RowMapper<User>>any(), anyString());
        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getRole(), actualUser.getRole());
        assertEquals(expectedUser.getNickname(), actualUser.getNickname());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
    }
}