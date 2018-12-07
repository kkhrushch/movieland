package com.khrushch.movieland.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Review {
    private long id;
    private long movieId;
    private User user;
    private String text;


    public long getId() {
        return id;
    }

    public long getMovieId() {
        return movieId;
    }

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id == review.id &&
                Objects.equals(user, review.user) &&
                Objects.equals(text, review.text);
    }
}
