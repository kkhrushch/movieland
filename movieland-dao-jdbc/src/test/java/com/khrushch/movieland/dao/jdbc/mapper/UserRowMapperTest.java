package com.khrushch.movieland.dao.jdbc.mapper;

import com.khrushch.movieland.model.User;
import com.khrushch.movieland.model.security.UserRole;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRowMapperTest {

    @Test
    public void testMapRow() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getLong("id")).thenReturn(1L);
        when(mockResultSet.getString("nickname")).thenReturn("aNickname");
        when(mockResultSet.getString("role")).thenReturn("aRole");
        when(mockResultSet.getString("password")).thenReturn("aPassword");

        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setNickname("aNickname");
        expectedUser.setRole("aRole");
        expectedUser.setPassword("aPassword");

        UserRowMapper mapper = new UserRowMapper();
        User actualUser = mapper.mapRow(mockResultSet, 0);

        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getNickname(), actualUser.getNickname());
        assertEquals(expectedUser.getRole(), actualUser.getRole());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
    }
}