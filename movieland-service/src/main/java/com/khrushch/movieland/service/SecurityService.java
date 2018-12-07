package com.khrushch.movieland.service;

import com.khrushch.movieland.dto.UserCredentialsDto;
import com.khrushch.movieland.model.User;

public interface SecurityService {
    UserCredentialsDto doLogin(UserCredentialsDto userCredentials);

    void doLogout(String uuid);

    User getUserByUuid(String uuid);
}
