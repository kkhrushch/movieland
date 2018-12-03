package com.khrushch.movieland.service;

import com.khrushch.movieland.dto.UserCredentialsDto;
import com.khrushch.movieland.model.User;

public interface SecurityService {
    boolean isAuthorized(String uuid, String requestUrl, String httpMethod);

    UserCredentialsDto doLogin(UserCredentialsDto userCredentials);

    void doLogout(String uuid);

    User getUserByUuid(String uuid);
}
