package com.marcosilv7.narutodelivery.security.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@ApiModel("Informacion para registrar un nuevo usuario en la aplicacion")
public class RegisterUserDTO {

    @ApiModelProperty(notes = "Correo electronico",required = true)
    @NotNull
    @Size(min = 2,max = 40)
    @Email
    private String email;

    @ApiModelProperty(notes = "Nombre del usuario",required = true)
    @NotNull
    @Size(min = 2,max = 40)
    private String name;

    @ApiModelProperty(notes = "Apellidos del usuario",required = true)
    @Size(min = 2,max = 40)
    @NotNull
    private String lastName;

    @ApiModelProperty(notes = "Fecha de nacimiento del usuario")
    private Date bithDay;

    @ApiModelProperty(notes = "Contraseña del usuario",required = true)
    @Size(min = 8,max = 100)
    @NotNull
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBithDay() {
        return bithDay;
    }

    public void setBithDay(Date bithDay) {
        this.bithDay = bithDay;
    }
}
