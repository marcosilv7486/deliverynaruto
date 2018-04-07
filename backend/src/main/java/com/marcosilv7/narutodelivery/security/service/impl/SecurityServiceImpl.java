package com.marcosilv7.narutodelivery.security.service.impl;

import com.marcosilv7.narutodelivery.security.dao.projections.UserSummaryProjection;
import com.marcosilv7.narutodelivery.security.dao.repository.UserRepository;
import com.marcosilv7.narutodelivery.security.service.interfaces.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    @Autowired
    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserSummaryProjection> findByUsername(String username) {
        return userRepository.findByUserName(username);
    }
}
