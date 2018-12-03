package com.khrushch.movieland.model.security;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private String method;

    HttpMethod(String method) {
        this.method = method;
    }

    public static HttpMethod fromString(String method) {
        for (HttpMethod httpMethod : values()) {
            if (httpMethod.toString().equalsIgnoreCase(method)) {
                return httpMethod;
            }
        }
        throw new IllegalArgumentException("Failed to construct HttpMethod from string: " + method);
    }

    @Override
    public String toString() {
        return method;
    }
}
