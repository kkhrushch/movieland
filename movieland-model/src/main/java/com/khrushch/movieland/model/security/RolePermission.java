package com.khrushch.movieland.model.security;

import java.util.Objects;

public class RolePermission {
    private UserRole role;
    private ResourceEndpoint endpoint;

    public RolePermission(UserRole role, ResourceEndpoint endpoint) {
        this.role = role;
        this.endpoint = endpoint;
    }

    public UserRole getRole() {
        return role;
    }

    public ResourceEndpoint getEndpoint() {
        return endpoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermission that = (RolePermission) o;
        return Objects.equals(role, that.role) &&
                Objects.equals(endpoint, that.endpoint);
    }

}
