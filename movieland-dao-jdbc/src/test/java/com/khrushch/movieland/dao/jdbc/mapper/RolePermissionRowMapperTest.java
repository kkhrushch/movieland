package com.khrushch.movieland.dao.jdbc.mapper;

import com.khrushch.movieland.model.security.HttpMethod;
import com.khrushch.movieland.model.security.ResourceEndpoint;
import com.khrushch.movieland.model.security.RolePermission;
import com.khrushch.movieland.model.security.UserRole;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RolePermissionRowMapperTest {

    @Test
    public void testMapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString("role")).thenReturn("aRole");
        when(resultSet.getString("http_method")).thenReturn("GET");
        when(resultSet.getString("url_pattern")).thenReturn("aUrlPattern");

        UserRole role = new UserRole("aRole");
        ResourceEndpoint endpoint = new ResourceEndpoint("aUrlPattern", HttpMethod.GET);
        RolePermission expectedRolePermission = new RolePermission(role, endpoint);

        RolePermissionRowMapper mapper = new RolePermissionRowMapper();
        RolePermission actualRolePermission = mapper.mapRow(resultSet, 0);

        assertEquals(expectedRolePermission, actualRolePermission);
    }

}