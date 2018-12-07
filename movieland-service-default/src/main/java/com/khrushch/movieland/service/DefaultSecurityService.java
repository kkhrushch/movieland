package com.khrushch.movieland.service;

import com.khrushch.movieland.dto.UserCredentialsDto;
import com.khrushch.movieland.model.Session;
import com.khrushch.movieland.model.User;
import com.khrushch.movieland.service.exception.AuthenticationException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DefaultSecurityService implements SecurityService {
    // visibility for testing
    static final ConcurrentHashMap<String, Session> SESSIONS = new ConcurrentHashMap<>();

    @Value("${session.ttl.hours}")
    private long sessionTtlHours;

    private UserService userService;

    public DefaultSecurityService() {
    }

    // for testing
    DefaultSecurityService(ConcurrentHashMap<String, Session> SESSIONS) {
        this.SESSIONS.putAll(SESSIONS);
    }

    @Override
    public UserCredentialsDto doLogin(UserCredentialsDto userCredentials) {
        String email = userCredentials.getEmail();
        String openPassword = userCredentials.getPassword();
        if (email == null || openPassword == null) {
            throw new AuthenticationException("User credentials incomplete, email: " + email);
        }

        User user = userService.getByEmail(email);
        if (user == null || !BCrypt.checkpw(openPassword, user.getPassword())) {
            throw new AuthenticationException("Failed to find user of incorrect password, email: " + email);
        }
        user.setPassword(null);

        String uuid = UUID.randomUUID().toString();
        LocalDateTime expirationTime = LocalDateTime.now().plusHours(sessionTtlHours);

        Session session = new Session(user, expirationTime);
        SESSIONS.put(uuid, session);

        return new UserCredentialsDto(uuid, user.getNickname());
    }

    @Override
    public void doLogout(String uuid) {
        Session session = SESSIONS.remove(uuid);
        if (session == null){
            throw new AuthenticationException("User not authorized, uuid: " + uuid);
        }
    }

    public User getUserByUuid(String uuid) {
        if (uuid == null) {
            throw new AuthenticationException("UUID is null");
        }
        Session session = SESSIONS.get(uuid);
        if (session == null) {
            throw new AuthenticationException("Unauthorized, uuid: " + uuid);
        }
        return session.getUser();
    }

    @Scheduled(fixedDelayString = "${session.cleanup.interval.hours}", initialDelayString = "${session.cleanup.interval.hours}")
    void cleanUpExpiredSessions() {
        LocalDateTime now = LocalDateTime.now();
        SESSIONS.values().removeIf(s -> s.getExpirationTime().isBefore(now));
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
