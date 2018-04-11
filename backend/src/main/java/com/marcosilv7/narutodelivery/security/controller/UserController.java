package com.marcosilv7.narutodelivery.security.controller;

import com.marcosilv7.narutodelivery.configuration.api.Api;
import com.marcosilv7.narutodelivery.security.dto.ProfileUserDTO;
import com.marcosilv7.narutodelivery.security.dto.RegisterUserDTO;
import com.marcosilv7.narutodelivery.security.service.interfaces.SecurityService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@io.swagger.annotations.Api(value = Api.USER_PATH,
        description = "Operaciones sobre los usuarios",
        consumes="application/json")
@RestController
@RequestMapping(Api.USER_PATH)
public class UserController {

    private final SecurityService securityService;

    @Autowired
    public UserController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @ApiOperation(value = "Crear nuevo usuario", notes = "Crea un nuevo usuario segun la informacion enviada. " +
            "Asimismo, el email debe ser unico por usuario.",response = ProfileUserDTO.class)
    @PostMapping("/")
    public ProfileUserDTO createUser(@Valid @RequestBody RegisterUserDTO data){
        return securityService.createUser(data);
    }

}
