package com.marcosilv7.narutodelivery.security.dto;

public class UserScopeDTO {

    private String nameSpring;

    public UserScopeDTO(String nameSpring) {
        this.nameSpring = nameSpring;
    }

    public String getNameSpring() {
        return nameSpring;
    }

    public void setNameSpring(String nameSpring) {
        this.nameSpring = nameSpring;
    }
}
