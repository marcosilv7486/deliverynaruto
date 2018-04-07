package com.marcosilv7.narutodelivery.security.service.interfaces;

import com.marcosilv7.narutodelivery.security.dto.UserDTO;

import java.util.Optional;

public interface SecurityService {
    Optional<UserDTO> findByUsername(String username);
}
