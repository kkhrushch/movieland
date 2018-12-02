package com.khrushch.movieland.rest.v1.filter;

import com.khrushch.movieland.service.SecurityService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityFilter implements Filter {
    private SecurityService securityService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String uuid = httpServletRequest.getHeader("uuid");
        boolean isAuth = securityService.isAuthorized(uuid, httpServletRequest.getRequestURI(), httpServletRequest.getMethod());
        if (isAuth) {
            chain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
        securityService = context.getAutowireCapableBeanFactory().getBean(SecurityService.class);
    }

    @Override
    public void destroy() {

    }

    // for testing
    void setSecurityService(SecurityService securityService){
        this.securityService = securityService;
    }
}
