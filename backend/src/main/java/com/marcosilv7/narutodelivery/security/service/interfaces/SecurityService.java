package com.marcosilv7.narutodelivery.security.service.interfaces;

import com.marcosilv7.narutodelivery.security.dto.ProfileUserDTO;
import com.marcosilv7.narutodelivery.security.dto.RegisterUserDTO;
import com.marcosilv7.narutodelivery.security.dto.UserDTO;

import javax.validation.Valid;
import java.util.Optional;

public interface SecurityService {

    Optional<UserDTO> findByUsername(String username);

    ProfileUserDTO createUser(RegisterUserDTO data);
}
