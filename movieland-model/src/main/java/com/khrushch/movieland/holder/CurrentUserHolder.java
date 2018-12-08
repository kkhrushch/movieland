package com.khrushch.movieland.holder;

import com.khrushch.movieland.model.User;

public class CurrentUserHolder {
    private static final ThreadLocal<User> USER = new ThreadLocal<>();

    public static User getUser() {
        return USER.get();
    }

    public static void setUser(User user) {
        USER.set(user);
    }
}
