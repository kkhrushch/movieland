package com.khrushch.movieland.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Movie {
    private long id;
    private String russianName;
    private String nativeName;
    private int yearOfRelease;

    @JsonIgnore
    private List<Genre> genres;
    @JsonIgnore
    private String countryOfOrigin;
    @JsonIgnore
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

    public List<Genre> getGenres() {
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

    public void setGenres(List<Genre> genres) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;

        Movie movie = (Movie) o;

        if (getId() != movie.getId()) return false;
        if (getYearOfRelease() != movie.getYearOfRelease()) return false;
        if (Double.compare(movie.getRating(), getRating()) != 0) return false;
        if (Double.compare(movie.getPrice(), getPrice()) != 0) return false;
        if (getRussianName() != null ? !getRussianName().equals(movie.getRussianName()) : movie.getRussianName() != null)
            return false;
        if (getNativeName() != null ? !getNativeName().equals(movie.getNativeName()) : movie.getNativeName() != null)
            return false;
        if (getGenres() != null ? !getGenres().equals(movie.getGenres()) : movie.getGenres() != null) return false;
        if (getCountryOfOrigin() != null ? !getCountryOfOrigin().equals(movie.getCountryOfOrigin()) : movie.getCountryOfOrigin() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(movie.getDescription()) : movie.getDescription() != null)
            return false;
        return getPicturePath() != null ? getPicturePath().equals(movie.getPicturePath()) : movie.getPicturePath() == null;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", russianName='" + russianName + '\'' +
                ", nativeName='" + nativeName + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                ", genres=" + genres +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", price=" + price +
                ", picturePath='" + picturePath + '\'' +
                '}';
    }
}
