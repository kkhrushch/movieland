package com.khrushch.movieland.dao;

import com.khrushch.movieland.model.User;

public interface UserDao {
    User getByEmail(String email);
}
