package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.UserDao;
import com.khrushch.movieland.model.User;
import com.khrushch.movieland.model.security.UserRole;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class DefaultUserServiceTest {

    @Test
    public void testGetByEmail() {
        User expectedUser = new User();
        expectedUser.setId(0);
        expectedUser.setNickname("aNickname");
        expectedUser.setRole(new UserRole("aRole"));
        expectedUser.setPassword("aPassword");

        UserDao mockUserDao = mock(UserDao.class);
        when(mockUserDao.getByEmail("anEmail")).thenReturn(expectedUser);

        DefaultUserService defaultUserService = new DefaultUserService();
        defaultUserService.setUserDao(mockUserDao);

        User actualUser = defaultUserService.getByEmail("anEmail");

        verify(mockUserDao, times(1)).getByEmail("anEmail");
        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getRole(), actualUser.getRole());
        assertEquals(expectedUser.getNickname(), actualUser.getNickname());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());

    }

}