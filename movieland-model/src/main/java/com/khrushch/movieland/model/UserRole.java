package com.khrushch.movieland.model;

public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public static UserRole fromString (String role){
        for (UserRole value : values()) {
            if(value.name().equalsIgnoreCase(role.trim())){
                return value;
            }
        }
        throw new IllegalArgumentException("Failed to construct UserRole from string: " + role);
    }

}
