package com.khrushch.movieland.dao.jdbc;

import com.khrushch.movieland.dao.SecurityDao;
import com.khrushch.movieland.dao.jdbc.mapper.RolePermissionRowMapper;
import com.khrushch.movieland.model.security.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcSecurityDao implements SecurityDao {
    private static final String SELECT_ROLE_PERMISSIONS = "SELECT role, url_pattern, http_method FROM role_resource_permission";
    private static final RowMapper<RolePermission> ROLE_PERMISSION_ROW_MAPPER = new RolePermissionRowMapper();

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<RolePermission> getRolePermissions() {
        return jdbcTemplate.query(SELECT_ROLE_PERMISSIONS, ROLE_PERMISSION_ROW_MAPPER);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
