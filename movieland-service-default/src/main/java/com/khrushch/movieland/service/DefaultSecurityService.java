package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.SecurityDao;
import com.khrushch.movieland.dto.UserCredentialsDto;
import com.khrushch.movieland.model.Session;
import com.khrushch.movieland.model.User;
import com.khrushch.movieland.model.security.ResourceEndpoint;
import com.khrushch.movieland.model.security.RolePermission;
import com.khrushch.movieland.model.security.UserRole;
import com.khrushch.movieland.service.exception.AuthenticationException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.*;

@Service
public class DefaultSecurityService implements SecurityService {
    private static final ConcurrentHashMap<String, Session> SESSIONS = new ConcurrentHashMap<>();
    private static final UserRole DEFAULT_ROLE = new UserRole("GUEST");

    private Map<UserRole, List<ResourceEndpoint>> rolePermission;

    @Value("${session.ttl.hours}")
    private long sessionTtlHours;

    private SecurityDao securityDao;

    private UserService userService;

    @Override
    public UserCredentialsDto doLogin(UserCredentialsDto userCredentials) {
        String email = userCredentials.getEmail();
        String openPassword = userCredentials.getPassword();
        if (email == null || openPassword == null) {
            throw new AuthenticationException();
        }

        User user = userService.getByEmail(email);
        if (user == null || !BCrypt.checkpw(openPassword, user.getPassword())) {
            throw new AuthenticationException();
        }

        String uuid = UUID.randomUUID().toString();
        LocalDateTime expirationTime = LocalDateTime.now().plusHours(sessionTtlHours);

        User userWithoutPassword = new User();
        userWithoutPassword.setId(user.getId());
        userWithoutPassword.setNickname(user.getNickname());
        userWithoutPassword.setRole(user.getRole());

        Session session = new Session(userWithoutPassword, expirationTime);
        SESSIONS.put(uuid, session);

        return new UserCredentialsDto(uuid, userWithoutPassword.getNickname());
    }

    @Override
    public void doLogout(String uuid) {
        SESSIONS.remove(uuid);
    }

    @Override
    public boolean isAuthorized(String uuid, String requestUrl, String httpMethod) {
        UserRole role = uuid == null ? DEFAULT_ROLE : getUserRole(uuid);
        return rolePermission.entrySet().stream()
                .filter(e -> e.getKey().equals(role))
                .flatMap(e -> e.getValue().stream())
                .anyMatch(p -> requestUrl.matches(p.getUrlPattern()));
    }

    @PostConstruct
    void init() {
        rolePermission = securityDao.getRolePermissions().stream()
                .collect(groupingBy(RolePermission::getRole, mapping(RolePermission::getEndpoint, toList())));
    }

    @Scheduled(fixedDelayString = "${session.cleanup.interval.hours}", initialDelayString = "${session.cleanup.interval.hours}")
    void cleanUpExpiredSessions() {
        LocalDateTime now = LocalDateTime.now();
        SESSIONS.values().removeIf(s -> s.getExpirationTime().isBefore(now));
    }

    private UserRole getUserRole(String uuid) {
        Session session = SESSIONS.get(uuid);
        if (session != null && session.getExpirationTime().isBefore(LocalDateTime.now())) {
            SESSIONS.remove(uuid);
            throw new AuthenticationException();
        }
        return session == null ? DEFAULT_ROLE : session.getUser().getRole();
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setSecurityDao(SecurityDao securityDao) {
        this.securityDao = securityDao;
    }
}
