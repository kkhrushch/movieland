package com.khrushch.movieland.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.*;

@Configuration
@EnableScheduling
@PropertySource("classpath:app-web.properties")
public class TaskExecutorConfig {
    private int corePoolSize;
    private int maximumPoolSize;
    private long keepAliveTimeSec;

    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Bean
    public ExecutorService executorService() {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTimeSec, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
    }

    @Value("${core.pool.size}")
    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    @Value("${maximum.pool.size}")
    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    @Value("${keep.alive.time.sec}")
    public void setKeepAliveTimeSec(long keepAliveTimeSec) {
        this.keepAliveTimeSec = keepAliveTimeSec;
    }
}
