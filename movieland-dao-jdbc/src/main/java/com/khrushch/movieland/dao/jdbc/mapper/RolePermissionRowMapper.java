package com.khrushch.movieland.dao.jdbc.mapper;

import com.khrushch.movieland.model.security.HttpMethod;
import com.khrushch.movieland.model.security.ResourceEndpoint;
import com.khrushch.movieland.model.security.RolePermission;
import com.khrushch.movieland.model.security.UserRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RolePermissionRowMapper implements RowMapper<RolePermission> {


    @Override
    public RolePermission mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserRole role = new UserRole(rs.getString("role"));
        HttpMethod method = HttpMethod.fromString(rs.getString("http_method"));
        ResourceEndpoint endpoint = new ResourceEndpoint(rs.getString("url_pattern"), method);

        return new RolePermission(role, endpoint);
    }
}
