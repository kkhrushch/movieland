package com.khrushch.movieland.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Objects;

public class Movie {
    private long id;
    private String russianName;
    private String nativeName;
    private int yearOfRelease;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Genre> genres;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Country> countries;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Review> reviews;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    private double rating;
    private double price;
    private String picturePath;

    public long getId() {
        return id;
    }

    public String getRussianName() {
        return russianName;
    }

    public String getNativeName() {
        return nativeName;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }

    public double getPrice() {
        return price;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRussianName(String russianName) {
        this.russianName = russianName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id &&
                yearOfRelease == movie.yearOfRelease &&
                Double.compare(movie.rating, rating) == 0 &&
                Double.compare(movie.price, price) == 0 &&
                Objects.equals(russianName, movie.russianName) &&
                Objects.equals(nativeName, movie.nativeName) &&
                Objects.equals(genres, movie.genres) &&
                Objects.equals(countries, movie.countries) &&
                Objects.equals(reviews, movie.reviews) &&
                Objects.equals(description, movie.description) &&
                Objects.equals(picturePath, movie.picturePath);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", russianName='" + russianName + '\'' +
                ", nativeName='" + nativeName + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                ", genres=" + genres +
                ", countries=" + countries +
                ", reviews=" + reviews +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", price=" + price +
                ", picturePath='" + picturePath + '\'' +
                '}';
    }
}
