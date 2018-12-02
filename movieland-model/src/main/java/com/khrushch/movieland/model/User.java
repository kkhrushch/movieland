package com.khrushch.movieland.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khrushch.movieland.model.security.UserRole;

import java.util.Objects;

public class User {
    private long id;
    private String nickname;
    @JsonIgnore
    private UserRole role;
    @JsonIgnore
    private String password;

    public User() {
    }

    public User(long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public User(long id, String nickname, UserRole role) {
        this(id, nickname);
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public UserRole getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
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
