package com.marcosilv7.narutodelivery.security.controller;

import com.marcosilv7.narutodelivery.configuration.api.Api;
import com.marcosilv7.narutodelivery.security.dto.RegisterUserDTO;
import com.marcosilv7.narutodelivery.security.service.interfaces.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(Api.USER_PATH)
public class UserController {

    private final SecurityService securityService;

    @Autowired
    public UserController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/")
    public void createUser(@Valid RegisterUserDTO data){
        securityService.createUser(data);
    }
}
