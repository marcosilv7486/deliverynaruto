package com.marcosilv7.narutodelivery.security.dto;

import java.time.LocalDate;

public class ProfileUserDTO {

    private Long id;
    private String email;
    private String fullName;
    private String avatar;
    private LocalDate birthDay;
    private String phone;

    public ProfileUserDTO(){

    }

    public ProfileUserDTO(Long id, String email,String fullName, String avatar, LocalDate birthDay, String phone) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.avatar = avatar;
        this.birthDay = birthDay;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
