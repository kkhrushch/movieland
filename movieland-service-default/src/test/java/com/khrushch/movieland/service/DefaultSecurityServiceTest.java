package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.SecurityDao;
import com.khrushch.movieland.dao.UserDao;
import com.khrushch.movieland.dto.UserCredentialsDto;
import com.khrushch.movieland.model.Session;
import com.khrushch.movieland.model.User;
import com.khrushch.movieland.model.security.HttpMethod;
import com.khrushch.movieland.model.security.ResourceEndpoint;
import com.khrushch.movieland.model.security.RolePermission;
import com.khrushch.movieland.model.security.UserRole;
import com.khrushch.movieland.service.exception.AuthenticationException;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
        UserRole role = new UserRole("USER");
        user.setRole(role);
        sessions.put("uuid-1", new Session(user, LocalDateTime.now().plusHours(1)));

        Map<UserRole, List<ResourceEndpoint>> permissions = new HashMap<>();
        permissions.put(role, Arrays.asList(new ResourceEndpoint("/v1/movie", HttpMethod.GET)));

        DefaultSecurityService defaultSecurityService = new DefaultSecurityService(permissions, sessions);

        boolean isAuth = defaultSecurityService.isAuthorized("uuid-1", "/v1/movie", "GET");

        assertTrue(isAuth);

        defaultSecurityService.doLogout("uuid-1");

        isAuth = defaultSecurityService.isAuthorized("uuid-1", "/v1/movie", "GET");

        assertFalse(isAuth);

    }

    @Test
    public void testDoLogout_With_InalidUuid() {
        ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();
        User user = new User();
        UserRole role = new UserRole("USER");
        user.setRole(role);
        sessions.put("uuid-1", new Session(user, LocalDateTime.now().plusHours(1)));

        Map<UserRole, List<ResourceEndpoint>> permissions = new HashMap<>();
        permissions.put(role, Arrays.asList(new ResourceEndpoint("/v1/movie", HttpMethod.GET)));

        DefaultSecurityService defaultSecurityService = new DefaultSecurityService(permissions, sessions);

        boolean isAuth = defaultSecurityService.isAuthorized("uuid-1", "/v1/movie", "GET");
        assertTrue(isAuth);

        defaultSecurityService.doLogout("uuid-2");
        isAuth = defaultSecurityService.isAuthorized("uuid-1", "/v1/movie", "GET");
        assertTrue(isAuth);

        isAuth = defaultSecurityService.isAuthorized("uuid-2", "/v1/movie", "GET");
        assertFalse(isAuth);

    }

    @Test
    public void testIsAuthorized_With_ValidUuid_And_GuestRole() {
        ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();
        User user = new User();
        UserRole role = new UserRole("GUEST");
        user.setRole(role);
        sessions.put("uuid-1", new Session(user, LocalDateTime.now().plusHours(1)));

        Map<UserRole, List<ResourceEndpoint>> permissions = new HashMap<>();
        permissions.put(role, Arrays.asList(new ResourceEndpoint("/v1/movie", HttpMethod.GET)));

        DefaultSecurityService defaultSecurityService = new DefaultSecurityService(permissions, sessions);
        boolean isAuth = defaultSecurityService.isAuthorized("uuid-1", "/v1/movie", "GET");

        assertTrue(isAuth);
    }

    @Test
    public void testIsAuthorized_With_InvalidUuid_And_AllowedResourceForGuest() {
        ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();
        User user = new User();
        UserRole role = new UserRole("GUEST");
        user.setRole(role);
        sessions.put("uuid-1", new Session(user, LocalDateTime.now().plusHours(1)));

        Map<UserRole, List<ResourceEndpoint>> permissions = new HashMap<>();
        permissions.put(role, Arrays.asList(new ResourceEndpoint("/v1/movie", HttpMethod.GET)));

        DefaultSecurityService defaultSecurityService = new DefaultSecurityService(permissions, sessions);
        boolean isAuth = defaultSecurityService.isAuthorized("uuid-2", "/v1/movie", "GET");

        assertTrue(isAuth);
    }

    @Test
    public void testIsAuthorized_With_InvalidUuid_And_Not_AllowedResourceForGuest() {
        ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();
        User user = new User();
        UserRole role = new UserRole("GUEST");
        user.setRole(role);
        sessions.put("uuid-1", new Session(user, LocalDateTime.now().plusHours(1)));

        Map<UserRole, List<ResourceEndpoint>> permissions = new HashMap<>();
        permissions.put(role, Arrays.asList(new ResourceEndpoint("/v1/movie", HttpMethod.GET)));

        DefaultSecurityService defaultSecurityService = new DefaultSecurityService(permissions, sessions);
        boolean isAuth = defaultSecurityService.isAuthorized("uuid-2", "/v1/movie/33", "GET");

        assertFalse(isAuth);
    }

    @Test
    public void testIsAuthorized_With_ValidUuid_And_ResourceAllowedForUser() {
        ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();
        User user = new User();
        UserRole userRole = new UserRole("USER");
        user.setRole(userRole);
        sessions.put("uuid-1", new Session(user, LocalDateTime.now().plusHours(1)));

        Map<UserRole, List<ResourceEndpoint>> permissions = new HashMap<>();
        UserRole guestRole = new UserRole("GUEST");
        permissions.put(guestRole, Arrays.asList(new ResourceEndpoint("/v1/movie", HttpMethod.GET)));
        permissions.put(userRole, Arrays.asList(new ResourceEndpoint("/v1/movie", HttpMethod.POST)));

        DefaultSecurityService defaultSecurityService = new DefaultSecurityService(permissions, sessions);
        boolean isAuth = defaultSecurityService.isAuthorized("uuid-1", "/v1/movie", "POST");

        assertTrue(isAuth);
    }

    @Test(expected = AuthenticationException.class)
    public void testIsAuthorized_With_SessionTimeout() {
        ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();
        User user = new User();
        UserRole role = new UserRole("GUEST");
        user.setRole(role);
        sessions.put("uuid-1", new Session(user, LocalDateTime.now().minusHours(1)));

        Map<UserRole, List<ResourceEndpoint>> permissions = new HashMap<>();
        permissions.put(role, Arrays.asList(new ResourceEndpoint("/v1/movie", HttpMethod.GET)));

        DefaultSecurityService defaultSecurityService = new DefaultSecurityService(permissions, sessions);
        boolean isAuth = defaultSecurityService.isAuthorized("uuid-1", "/v1/movie", "GET");

    }

    @Test
    public void testInit(){
        SecurityDao mockSecurityDao = mock(SecurityDao.class);

        DefaultSecurityService defaultSecurityService = new DefaultSecurityService();
        defaultSecurityService.setSecurityDao(mockSecurityDao);

        defaultSecurityService.init();

        verify(mockSecurityDao, times(1)).getRolePermissions();
    }

}