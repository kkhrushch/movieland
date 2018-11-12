package com.khrushch.movieland.model;

import java.util.List;

public class Movie {
    private long id;
    private String russianName;
    private String nativeName;
    private List<String> genres;
    private int yearOfRelease;
    private String countryOfOrigin;
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

    public List<String> getGenres() {
        return genres;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
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

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
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
}
