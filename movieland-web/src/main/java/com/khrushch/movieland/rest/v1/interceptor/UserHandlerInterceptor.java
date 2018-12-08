package com.khrushch.movieland.rest.v1.interceptor;

import com.khrushch.movieland.holder.CurrentUserHolder;
import com.khrushch.movieland.model.User;
import com.khrushch.movieland.model.UserRole;
import com.khrushch.movieland.rest.v1.annotation.ProtectedBy;
import com.khrushch.movieland.service.SecurityService;
import com.khrushch.movieland.service.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class UserHandlerInterceptor implements HandlerInterceptor {
    private SecurityService securityService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        User user = securityService.getUserByUuid(request.getHeader("uuid"));

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            validateUserRole(handlerMethod, user);
        }

        CurrentUserHolder.setUser(user);
        return true;
    }

    private void validateUserRole(HandlerMethod handlerMethod, User user) {
        ProtectedBy protectedBy = handlerMethod.getMethodAnnotation(ProtectedBy.class);
        if (protectedBy == null) {
            return;
        }

        UserRole[] acceptedRoles = protectedBy.acceptedRoles();
        boolean isRoleAccepted = Arrays.stream(acceptedRoles).anyMatch(r -> r == user.getRole());
        if (!isRoleAccepted) {
            throw new AuthenticationException("User role not accepted; user role: " + user.getRole() + ", required roles: " + Arrays.toString(acceptedRoles));
        }
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
