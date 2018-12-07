package com.khrushch.movieland.rest.v1.interceptor;

import com.khrushch.movieland.service.SecurityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserHandlerInterceptorTest {
    @Mock
    SecurityService mockSecurityService;

    @InjectMocks
    UserHandlerInterceptor userHandlerInterceptor;

    @Test
    public void preHandle() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);

        userHandlerInterceptor.preHandle(mockRequest, mockResponse, new Object());

        verify(mockRequest, times(1)).getHeader("uuid");
        verify(mockSecurityService, times(1)).getUserByUuid(anyString());
    }
}