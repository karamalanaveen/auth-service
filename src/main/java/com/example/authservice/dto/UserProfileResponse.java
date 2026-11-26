package com.example.authservice.dto;

import java.util.Collection;

public class UserProfileResponse {

    private String username;
    private Collection<?> authorities;
    private String message;

    public UserProfileResponse() {
    }

    public UserProfileResponse(String username, Collection<?> authorities, String message) {
        this.username = username;
        this.authorities = authorities;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<?> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<?> authorities) {
        this.authorities = authorities;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
