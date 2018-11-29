package com.khrushch.movieland.model;

import java.util.Objects;

public class User {
    private long id;
    private String nickname;

    public User() {
    }

    public User(long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(nickname, user.nickname);
    }
}
