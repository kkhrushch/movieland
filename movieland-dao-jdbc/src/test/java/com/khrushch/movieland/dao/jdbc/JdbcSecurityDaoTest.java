package com.khrushch.movieland.dao.jdbc;

import com.khrushch.movieland.model.Genre;
import com.khrushch.movieland.model.security.HttpMethod;
import com.khrushch.movieland.model.security.ResourceEndpoint;
import com.khrushch.movieland.model.security.RolePermission;
import com.khrushch.movieland.model.security.UserRole;
import org.junit.Test;
import org.mockito.Matchers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class JdbcSecurityDaoTest {

    @Test
    public void testGetRolePermissions() {
        JdbcTemplate mockJdbcTemplate = mock(JdbcTemplate.class);
        when(mockJdbcTemplate.query(anyString(), Matchers.<RowMapper<RolePermission>>any())).thenReturn(getTestRolePermissions());

        JdbcSecurityDao securityDao = new JdbcSecurityDao();
        securityDao.setJdbcTemplate(mockJdbcTemplate);

        List<RolePermission> actualRolePermissions = securityDao.getRolePermissions();

        verify(mockJdbcTemplate, times(1)).query(anyString(), Matchers.<RowMapper<RolePermission>>any());
        assertEquals(getTestRolePermissions(), actualRolePermissions);
    }

    private List<RolePermission> getTestRolePermissions() {
        UserRole roleOne = new UserRole("roleOne");
        ResourceEndpoint endpointOne = new ResourceEndpoint("urlPatternOne", HttpMethod.GET);
        RolePermission first = new RolePermission(roleOne, endpointOne);

        UserRole roleTwo = new UserRole("roleTwo");
        ResourceEndpoint endpointTwo = new ResourceEndpoint("urlPatternTwo", HttpMethod.POST);
        RolePermission second = new RolePermission(roleTwo, endpointTwo);

        return Arrays.asList(first, second);
    }
}