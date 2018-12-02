package com.khrushch.movieland.service;

import com.khrushch.movieland.model.User;

public interface UserService {
    User getByEmail(String email);
}
