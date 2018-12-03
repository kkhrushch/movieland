package com.khrushch.movieland.dao.jdbc.mapper;

import com.khrushch.movieland.model.User;
import com.khrushch.movieland.model.security.UserRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setNickname(rs.getString("nickname"));
        user.setRole(new UserRole(rs.getString("role")));
        user.setPassword(rs.getString("password"));

        return user;
    }
}
