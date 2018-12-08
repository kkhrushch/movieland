package com.khrushch.movieland.rest.v1.controller;

import com.khrushch.movieland.dto.UserCredentialsDto;
import com.khrushch.movieland.holder.CurrentUserHolder;
import com.khrushch.movieland.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    private SecurityService securityService;

    @PostMapping("/login")
    public UserCredentialsDto doLogin(@RequestBody UserCredentialsDto userCredentials) {
        return securityService.doLogin(userCredentials);
    }

    @DeleteMapping("/logout")
    public void doLogout() {
        securityService.doLogout(CurrentUserHolder.getUser());
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
