package com.khrushch.movieland.dao.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("classpath:app-dao-rest.properties")
public class RestConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
