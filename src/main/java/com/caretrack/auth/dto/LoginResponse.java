package com.caretrack.auth.dto;

import java.util.Set;

public class LoginResponse {
    private String token;
    private String tokenType = "Bearer";
    private long expiresIn;
    private AuthUserInfo user;
//Empty constructor (needed for JSON serialization/deserialization).
    public LoginResponse() {}
//Constructor to quickly create a response object with values.
    public LoginResponse(String token, long expiresIn, AuthUserInfo user) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.user = user;
    }
//This contains the user details you send back after login.Nested class inside LoginResponse.
//✔ Static → does not need outer class instance...
    public static class AuthUserInfo {
        private Long id;
        private String email;
        private String fullName;
        private Set<String> roles;

        public AuthUserInfo() {}

        public AuthUserInfo(Long id, String email, String fullName, Set<String> roles) {
            this.id = id;
            this.email = email;
            this.fullName = fullName;
            this.roles = roles;
        }

        public Long getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getFullName() {
            return fullName;
        }

        public Set<String> getRoles() {
            return roles;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public void setRoles(Set<String> roles) {
            this.roles = roles;
        }
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public AuthUserInfo getUser() {
        return user;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setUser(AuthUserInfo user) {
        this.user = user;
    }
}
