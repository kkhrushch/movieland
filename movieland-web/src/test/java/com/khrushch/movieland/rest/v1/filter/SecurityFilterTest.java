package com.khrushch.movieland.rest.v1.filter;

import com.khrushch.movieland.service.SecurityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SecurityFilterTest {
    @Mock
    private HttpServletRequest mockHttpServletRequest;

    @Mock
    private HttpServletResponse mockHttpServletResponse;

    @Mock
    private FilterChain mockFilterChain;

    @Mock
    private SecurityService mockSecurityService;

    @InjectMocks
    private SecurityFilter securityFilter;

    private String uuid = "UUID12345";
    private String requestUri = "/v1/something";
    private String method = "GET";

    @Before
    public void setUp() {
        when(mockHttpServletRequest.getHeader("uuid")).thenReturn(uuid);
        when(mockHttpServletRequest.getRequestURI()).thenReturn(requestUri);
        when(mockHttpServletRequest.getMethod()).thenReturn(method);
    }

    @Test
    public void testDoFilterWithAuthorized() throws IOException, ServletException {
        boolean isAuth = true;
        when(mockSecurityService.isAuthorized(uuid, requestUri, method)).thenReturn(isAuth);

        securityFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);

        verify(mockSecurityService, times(1)).isAuthorized(uuid, requestUri, method);
        verify(mockFilterChain, times(1)).doFilter(mockHttpServletRequest, mockHttpServletResponse);
    }

    @Test
    public void testDoFilterWithNotAuthorized() throws IOException, ServletException {
        boolean isAuth = false;
        when(mockSecurityService.isAuthorized(uuid, requestUri, method)).thenReturn(isAuth);

        securityFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);

        verify(mockSecurityService, times(1)).isAuthorized(uuid, requestUri, method);
        verify(mockHttpServletResponse, times(1)).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(mockFilterChain, times(0)).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }

}