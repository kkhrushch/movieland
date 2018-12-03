package com.khrushch.movieland.model;

import java.time.LocalDateTime;

public class Session {
    private User user;
    private LocalDateTime expirationTime;

    public Session(User user, LocalDateTime expirationTime) {
        this.user = user;
        this.expirationTime = expirationTime;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }
}
