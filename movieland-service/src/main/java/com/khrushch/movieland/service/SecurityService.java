package com.khrushch.movieland.service;

import com.khrushch.movieland.dto.UserCredentialsDto;

public interface SecurityService {
    boolean isAuthorized(String uuid, String requestUrl, String httpMethod);

    UserCredentialsDto doLogin(UserCredentialsDto userCredentials);

    void doLogout(String uuid);

}
