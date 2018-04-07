package com.marcosilv7.narutodelivery.security.authentication.login;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;


import java.util.List;

public class UserContext {

    private final Long userId;
    private final String userName;
    private final String fullName;
    private final String avatar;
    private final List<GrantedAuthority> authorities;

    private UserContext(Long userId, String userName, String fullName, String avatar, List<GrantedAuthority> authorities){
        this.userId = userId;
        this.userName = userName;
        this.fullName = fullName;
        this.avatar = avatar;
        this.authorities = authorities;
    }

    public static UserContext create(Long userId,String userName, String fullName,String avatar, List<GrantedAuthority> authorities){
        if(StringUtils.isBlank(userName)){
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacio");
        }
        return new UserContext(userId, userName, fullName, avatar, authorities);
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getAvatar() {
        return avatar;
    }
}
