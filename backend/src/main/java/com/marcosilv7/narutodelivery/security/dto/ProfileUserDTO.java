package com.marcosilv7.narutodelivery.security.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.util.Date;

@ApiModel("Perfil de Usuario")
public class ProfileUserDTO {
    @ApiModelProperty(notes = "Identificador del usuario")
    private Long id;
    @ApiModelProperty(notes = "Correo del usuario")
    private String email;
    @ApiModelProperty(notes = "Nombre completo del usuario")
    private String fullName;
    @ApiModelProperty(notes = "Avatar del usuario")
    private String avatar;
    @ApiModelProperty(notes = "Fecha de Cumpleaños del usuario")
    private Date birthDay;
    @ApiModelProperty(notes = "Telefono del usuario")
    private String phone;

    public ProfileUserDTO(){

    }

    public ProfileUserDTO(Long id, String email,String fullName, String avatar, Date birthDay, String phone) {
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

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
