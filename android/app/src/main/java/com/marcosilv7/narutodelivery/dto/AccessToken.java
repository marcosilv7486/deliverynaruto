package com.marcosilv7.narutodelivery.dto;

public class AccessToken {
    private String accesToken;
    private String refreshToken;

    public AccessToken(String accesToken, String refreshToken) {
        this.accesToken = accesToken;
        this.refreshToken = refreshToken;
    }

    public String getAccesToken() {
        return accesToken;
    }

    public void setAccesToken(String accesToken) {
        this.accesToken = accesToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
