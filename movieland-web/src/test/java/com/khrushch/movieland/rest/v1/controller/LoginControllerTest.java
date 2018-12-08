package com.khrushch.movieland.rest.v1.controller;

import com.khrushch.movieland.dto.UserCredentialsDto;
import com.khrushch.movieland.model.User;
import com.khrushch.movieland.holder.CurrentUserHolder;
import com.khrushch.movieland.model.UserRole;
import com.khrushch.movieland.service.SecurityService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

    @Test
    public void testDoLogin() {
        UserCredentialsDto inputCredentials = new UserCredentialsDto();
        inputCredentials.setEmail("anEmail");
        inputCredentials.setPassword("aPassword");

        UserCredentialsDto outputCredentials = new UserCredentialsDto("uuid-12345", "aNickname");

        SecurityService mockSecurityService = mock(SecurityService.class);
        when(mockSecurityService.doLogin(inputCredentials)).thenReturn(outputCredentials);

        LoginController loginController = new LoginController();
        loginController.setSecurityService(mockSecurityService);

        UserCredentialsDto actualCredentials = loginController.doLogin(inputCredentials);

        verify(mockSecurityService, times(1)).doLogin(inputCredentials);
        assertEquals(outputCredentials.getUuid(), actualCredentials.getUuid());
        assertEquals(outputCredentials.getNickname(), actualCredentials.getNickname());
        assertNull(actualCredentials.getEmail());
        assertNull(actualCredentials.getPassword());
    }

    @Test
    public void testDoLogout() {
        User user = new User();
        user.setId(1);
        user.setNickname("aNickname");
        user.setRole(UserRole.USER);

        SecurityService mockSecurityService = mock(SecurityService.class);

        LoginController loginController = new LoginController();
        loginController.setSecurityService(mockSecurityService);

        CurrentUserHolder.setUser(user);
        loginController.doLogout();

        verify(mockSecurityService, times(1)).doLogout(user);
    }
}