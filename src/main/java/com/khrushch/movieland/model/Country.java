package com.khrushch.movieland.model;

import java.util.Objects;

public class Country {
    private long id;
    private String name;

    public Country(){

    }

    public Country(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return id == country.id &&
                Objects.equals(name, country.name);
    }

}
