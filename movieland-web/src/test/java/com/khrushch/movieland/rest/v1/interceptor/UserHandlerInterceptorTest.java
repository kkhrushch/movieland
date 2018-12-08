package com.khrushch.movieland.rest.v1.interceptor;

import com.khrushch.movieland.model.User;
import com.khrushch.movieland.model.UserRole;
import com.khrushch.movieland.rest.v1.annotation.ProtectedBy;
import com.khrushch.movieland.service.SecurityService;
import com.khrushch.movieland.service.exception.AuthenticationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserHandlerInterceptorTest {
    @Mock
    SecurityService mockSecurityService;

    @InjectMocks
    UserHandlerInterceptor userHandlerInterceptor;

    @Test
    public void preHandle_With_ValidUserRole() {
        User user = new User();
        user.setId(1);
        user.setRole(UserRole.USER);
        user.setNickname("aNickname");

        String uuid = "uuid-12345";

        when(mockSecurityService.getUserByUuid(uuid)).thenReturn(user);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getHeader("uuid")).thenReturn(uuid);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);

        ProtectedBy mockProtectedBy = mock(ProtectedBy.class);
        UserRole[] userRoles = {UserRole.USER};
        when(mockProtectedBy.acceptedRoles()).thenReturn(userRoles);
        HandlerMethod mockHandlerMethod = mock(HandlerMethod.class);
        when(mockHandlerMethod.getMethodAnnotation(ProtectedBy.class)).thenReturn(mockProtectedBy);

        boolean preHandleResult = userHandlerInterceptor.preHandle(mockRequest, mockResponse, mockHandlerMethod);

        assertTrue(preHandleResult);
        verify(mockRequest, times(1)).getHeader("uuid");
        verify(mockSecurityService, times(1)).getUserByUuid(uuid);

    }

    @Test(expected = AuthenticationException.class)
    public void preHandle_With_InValidUserRole() {
        User user = new User();
        user.setId(1);
        user.setRole(UserRole.ADMIN);
        user.setNickname("aNickname");

        String uuid = "uuid-12345";

        when(mockSecurityService.getUserByUuid(uuid)).thenReturn(user);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getHeader("uuid")).thenReturn(uuid);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);

        ProtectedBy mockProtectedBy = mock(ProtectedBy.class);
        UserRole[] userRoles = {UserRole.USER};
        when(mockProtectedBy.acceptedRoles()).thenReturn(userRoles);
        HandlerMethod mockHandlerMethod = mock(HandlerMethod.class);
        when(mockHandlerMethod.getMethodAnnotation(ProtectedBy.class)).thenReturn(mockProtectedBy);

        boolean preHandleResult = userHandlerInterceptor.preHandle(mockRequest, mockResponse, mockHandlerMethod);

    }
}