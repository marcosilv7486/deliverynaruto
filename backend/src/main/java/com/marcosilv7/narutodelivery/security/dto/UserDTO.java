package com.marcosilv7.narutodelivery.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

public class UserDTO {
    private Long id;
    private String username;
    private String fullName;
    private String avatar;
    @JsonIgnore
    private boolean enabled;
    @JsonIgnore
    private String password;
    private Date lastLogin;
    @JsonIgnore
    private List<UserScopeDTO> scopes;

    public UserDTO(Long id, String username, String fullName, String avatar, boolean enabled, String password, Date lastLogin) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.avatar = avatar;
        this.enabled = enabled;
        this.password = password;
        this.lastLogin = lastLogin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public List<UserScopeDTO> getScopes() {
        return scopes;
    }

    public void setScopes(List<UserScopeDTO> scopes) {
        this.scopes = scopes;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
