package com.khrushch.movieland.rest.v1.interceptor;

import com.khrushch.movieland.model.User;
import com.khrushch.movieland.rest.v1.holder.CurrentUserHolder;
import com.khrushch.movieland.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserHandlerInterceptor implements HandlerInterceptor {
    private SecurityService securityService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        User user = securityService.getUserByUuid(request.getHeader("uuid"));
        CurrentUserHolder.setUser(user);
        return true;
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
