package com.khrushch.movieland.dto;

import java.util.Objects;

public class ReviewDto {
    private String uuid;
    private long movieId;
    private long userId;
    private String text;

    public String getUuid() {
        return uuid;
    }

    public long getMovieId() {
        return movieId;
    }

    public long getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewDto reviewDto = (ReviewDto) o;
        return movieId == reviewDto.movieId &&
                userId == reviewDto.userId &&
                Objects.equals(uuid, reviewDto.uuid) &&
                Objects.equals(text, reviewDto.text);
    }

}
