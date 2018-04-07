package com.marcosilv7.narutodelivery.core.controller;

import com.marcosilv7.narutodelivery.configuration.api.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Api.PROFILE_PATH)
public class ProfileController {

    @GetMapping("/{id}")
    public String findbyId(@PathVariable("id") Long id){
        return "HOLA ";
    }


}
