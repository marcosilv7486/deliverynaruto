package com.marcosilv7.narutodelivery.security.service.impl;

import com.marcosilv7.narutodelivery.security.dao.repository.UserRepository;
import com.marcosilv7.narutodelivery.security.dao.repository.UserScopeRepository;
import com.marcosilv7.narutodelivery.security.dto.UserDTO;
import com.marcosilv7.narutodelivery.security.service.interfaces.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;
    private final UserScopeRepository userScopeRepository;

    @Autowired
    public SecurityServiceImpl(UserRepository userRepository, UserScopeRepository userScopeRepository) {
        this.userRepository = userRepository;
        this.userScopeRepository = userScopeRepository;
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        Optional<UserDTO> userDTO = userRepository.findDtoWithConstructorExpression(username);
        userDTO.ifPresent(p -> p.setScopes(userScopeRepository.findDtoWithConstructorExpression(p.getId())));
        return userDTO;
    }
}
