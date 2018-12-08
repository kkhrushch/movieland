package com.khrushch.movieland.service;

import com.khrushch.movieland.dto.UserCredentialsDto;
import com.khrushch.movieland.model.Session;
import com.khrushch.movieland.model.User;
import com.khrushch.movieland.model.UserRole;
import com.khrushch.movieland.service.exception.AuthenticationException;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultSecurityServiceTest {

    @Test(expected = AuthenticationException.class)
    public void testDoLogin_With_NullPassword() {
        UserCredentialsDto userCredentialsDto = new UserCredentialsDto();
        userCredentialsDto.setEmail("someEmail");
        userCredentialsDto.setPassword(null);

        DefaultSecurityService defaultSecurityService = new DefaultSecurityService();
        defaultSecurityService.doLogin(userCredentialsDto);
    }

    @Test(expected = AuthenticationException.class)
    public void testDoLogin_With_NullEmail() {
        UserCredentialsDto userCredentialsDto = new UserCredentialsDto();
        userCredentialsDto.setEmail(null);
        userCredentialsDto.setPassword("somePassword");

        DefaultSecurityService defaultSecurityService = new DefaultSecurityService();
        defaultSecurityService.doLogin(userCredentialsDto);
    }

    @Test(expected = AuthenticationException.class)
    public void testDoLogin_With_InvalidEmail() {
        UserCredentialsDto userCredentialsDto = new UserCredentialsDto();
        userCredentialsDto.setEmail("someEmail");
        userCredentialsDto.setPassword("somePassword");

        UserService mockUserService = mock(UserService.class);
        when(mockUserService.getByEmail("someEmail")).thenReturn(null);

        DefaultSecurityService defaultSecurityService = new DefaultSecurityService();
        defaultSecurityService.setUserService(mockUserService);
        defaultSecurityService.doLogin(userCredentialsDto);

    }

    @Test(expected = AuthenticationException.class)
    public void testDoLogin_With_ValidEmail_And_InvalidPassword() {
        UserCredentialsDto userCredentialsDto = new UserCredentialsDto();
        userCredentialsDto.setEmail("someEmail");
        userCredentialsDto.setPassword("invalidPassword");

        User user = new User();
        String hashedPassword = BCrypt.hashpw("password", BCrypt.gensalt());
        user.setPassword(hashedPassword);
        UserService mockUserService = mock(UserService.class);
        when(mockUserService.getByEmail("someEmail")).thenReturn(user);

        DefaultSecurityService defaultSecurityService = new DefaultSecurityService();
        defaultSecurityService.setUserService(mockUserService);
        defaultSecurityService.doLogin(userCredentialsDto);

    }

    @Test
    public void testDoLogin_With_ValidEmail_And_ValidPassword() {
        UserCredentialsDto userCredentialsDto = new UserCredentialsDto();
        userCredentialsDto.setEmail("someEmail");
        userCredentialsDto.setPassword("somePassword");

        User user = new User();
        user.setNickname("someNickname");
        String hashedPassword = BCrypt.hashpw(userCredentialsDto.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        UserService mockUserService = mock(UserService.class);
        when(mockUserService.getByEmail("someEmail")).thenReturn(user);

        DefaultSecurityService defaultSecurityService = new DefaultSecurityService();
        defaultSecurityService.setUserService(mockUserService);

        UserCredentialsDto actualCredentialsDto = defaultSecurityService.doLogin(userCredentialsDto);

        assertEquals("someNickname", actualCredentialsDto.getNickname());
        assertNotNull(actualCredentialsDto.getUuid());
        assertNull(actualCredentialsDto.getEmail());
        assertNull(actualCredentialsDto.getPassword());

    }

    @Test
    public void testDoLogout_With_ValidUuid() {
        ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();
        User user = new User();
        user.setId(1);
        user.setRole(UserRole.USER);
        sessions.put("uuid-1", new Session(user, LocalDateTime.now().plusHours(1)));

        DefaultSecurityService defaultSecurityService = new DefaultSecurityService(sessions);

        boolean isAuth = DefaultSecurityService.SESSIONS.values().stream()
                .anyMatch(s -> s.getUser().getId() == 1);
        assertTrue(isAuth);

        defaultSecurityService.doLogout(user);
        isAuth = DefaultSecurityService.SESSIONS.values().stream()
                .anyMatch(s -> s.getUser().getId() == 1);
        assertFalse(isAuth);

    }

    @Test(expected = AuthenticationException.class)
    public void testDoLogout_With_InalidUuid() {
        ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();
        User user = new User();
        user.setId(1);
        user.setRole(UserRole.USER);
        sessions.put("uuid-1", new Session(user, LocalDateTime.now().plusHours(1)));

        DefaultSecurityService defaultSecurityService = new DefaultSecurityService(sessions);

        boolean isAuth = DefaultSecurityService.SESSIONS.values().stream()
                .anyMatch(s -> s.getUser().getId() == 1);
        assertTrue(isAuth);

        defaultSecurityService.doLogout(new User());
    }

    @Test
    public void testGetUserByUuid_With_NullUuid() {
        DefaultSecurityService defaultSecurityService = new DefaultSecurityService();
        User user = defaultSecurityService.getUserByUuid(null);

        assertNull(user);
    }

    @Test(expected = AuthenticationException.class)
    public void testGetUserByUuid_With_InvalidUuid() {
        ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();
        User expectedUser = new User();
        sessions.put("uuid-1", new Session(expectedUser, LocalDateTime.now().minusHours(1)));

        DefaultSecurityService defaultSecurityService = new DefaultSecurityService(sessions);
        defaultSecurityService.getUserByUuid("uuid-INVALID");
    }

    @Test(expected = AuthenticationException.class)
    public void testGetUserByUuid_With_ValidUuid_And_ExpiredSession() {
        ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();
        User expectedUser = new User();
        sessions.put("uuid-1", new Session(expectedUser, LocalDateTime.now().minusHours(1)));

        DefaultSecurityService defaultSecurityService = new DefaultSecurityService(sessions);
        User actualUser = defaultSecurityService.getUserByUuid("uuid-1");

    }

    @Test
    public void testGetUserByUuid_With_ValidUuid() {
        ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();
        User expectedUser = new User();
        sessions.put("uuid-1", new Session(expectedUser, LocalDateTime.now().plusHours(1)));

        DefaultSecurityService defaultSecurityService = new DefaultSecurityService(sessions);
        User actualUser = defaultSecurityService.getUserByUuid("uuid-1");

        assertEquals(expectedUser, actualUser);

    }

}