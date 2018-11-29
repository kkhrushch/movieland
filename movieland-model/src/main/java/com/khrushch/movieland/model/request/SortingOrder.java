package com.khrushch.movieland.model.request;

public enum SortingOrder {
    ASC,
    DESC;

    public static SortingOrder forName(String name) {
        for (SortingOrder value : values()) {
            if (value.name().equalsIgnoreCase(name.trim())) {
                return value;
            }
        }
        throw new IllegalArgumentException("Cannot get SortingOrder for name: " + name);
    }

}
