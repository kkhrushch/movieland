package com.khrushch.movieland.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DaoConfig {
    @Value("${driver.class.name}")
    private String driverClassName;

    @Value("${url}")
    private String url;

    @Value("${user}")
    private String user;

    @Value("${pass}")
    private String pass;

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

    private DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(pass);
        return dataSource;
    }

}
