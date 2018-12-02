package com.khrushch.movieland.dao.jdbc;

import com.khrushch.movieland.dao.UserDao;
import com.khrushch.movieland.dao.jdbc.mapper.UserRowMapper;
import com.khrushch.movieland.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserDao implements UserDao {
    private static final String SELECT_USER_BY_EMAIL = "SELECT id, nickname, role, password FROM app_user WHERE email = ?";
    private static final RowMapper<User> USER_ROW_MAPPER = new UserRowMapper();

    private JdbcTemplate jdbcTemplate;

    @Override
    public User getByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(SELECT_USER_BY_EMAIL, USER_ROW_MAPPER, email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
