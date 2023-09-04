package com.example.myoidpserver.service;

import com.sunteco.suncloud.lib.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CurrentUserService {

    private UserInfo currentUser;
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public CurrentUserService(HttpServletRequest httpServletRequest) {

        this.httpServletRequest = httpServletRequest;
    }

    public UserInfo getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        if (currentUser != null) {
            return currentUser;
        }

        try {
            currentUser = (UserInfo) authentication.getPrincipal();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return currentUser;
    }
}
