package com.khrushch.movieland.model.security;

import java.util.Objects;

public class ResourceEndpoint {
    private String urlPattern;
    private HttpMethod httpMethod;

    public ResourceEndpoint(String urlPattern, HttpMethod httpMethod) {
        this.urlPattern = urlPattern;
        this.httpMethod = httpMethod;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceEndpoint endpoint = (ResourceEndpoint) o;
        return Objects.equals(urlPattern, endpoint.urlPattern) &&
                httpMethod == endpoint.httpMethod;
    }

}
