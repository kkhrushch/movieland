package com.khrushch.movieland.common;

public enum SortingOrder {
    ASC,
    DESC;

    public static SortingOrder forName(String name){
        for (SortingOrder value : values()) {
            if(value.name().equalsIgnoreCase(name.trim())){
                return value;
            }
        }
        throw new IllegalArgumentException("Cannot get SortingOrder for name: " + name);
    }

    public static boolean isSortingOrder(String text){
        for (SortingOrder value : values()) {
            if(value.name().equalsIgnoreCase(text.trim())){
                return true;
            }
        }
        return false;
    }

}
