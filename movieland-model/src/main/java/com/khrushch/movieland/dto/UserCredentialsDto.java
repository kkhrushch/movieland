package com.khrushch.movieland.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCredentialsDto {
    private String email;
    private String password;
    private String uuid;
    private String nickname;

    public UserCredentialsDto() {

    }

    public UserCredentialsDto(String uuid, String nickname) {
        this.uuid = uuid;
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUuid() {
        return uuid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
