package com.marcosilv7.narutodelivery.security.service.impl;

import com.marcosilv7.narutodelivery.configuration.exceptions.BusinessException;
import com.marcosilv7.narutodelivery.security.dao.domain.User;
import com.marcosilv7.narutodelivery.security.dao.domain.UserScope;
import com.marcosilv7.narutodelivery.security.dao.repository.ScopeRepository;
import com.marcosilv7.narutodelivery.security.dao.repository.UserRepository;
import com.marcosilv7.narutodelivery.security.dao.repository.UserScopeRepository;
import com.marcosilv7.narutodelivery.security.dto.ProfileUserDTO;
import com.marcosilv7.narutodelivery.security.dto.RegisterUserDTO;
import com.marcosilv7.narutodelivery.security.dto.UserDTO;
import com.marcosilv7.narutodelivery.security.service.interfaces.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;
    private final UserScopeRepository userScopeRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ScopeRepository scopeRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final long ID_SCOPE_CREATE_ORDER = 1L;

    @Autowired
    public SecurityServiceImpl(UserRepository userRepository, UserScopeRepository userScopeRepository, BCryptPasswordEncoder passwordEncoder, ScopeRepository scopeRepository) {
        this.userRepository = userRepository;
        this.userScopeRepository = userScopeRepository;
        this.passwordEncoder = passwordEncoder;
        this.scopeRepository = scopeRepository;
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        Optional<UserDTO> userDTO = userRepository.findDtoWithConstructorExpression(username);
        userDTO.ifPresent(p -> p.setScopes(userScopeRepository.findDtoWithConstructorExpression(p.getId())));
        return userDTO;
    }

    @Override
    @Transactional
    public ProfileUserDTO createUser(RegisterUserDTO data) {
        Optional<UserDTO> user = userRepository.findDtoWithConstructorExpression(data.getEmail());
        if(user.isPresent()){
            throw new BusinessException("El correo ya se encuentra en uso.");
        }
        User newUser = new User();
        newUser.setEnabled(true);
        newUser.setFullName(data.getName()+" "+data.getLastName());
        newUser.setUserName(data.getEmail());
        newUser.setPassword(passwordEncoder.encode(data.getPassword()));
        newUser.setPhone(data.getPhone());
        newUser.setBithDay(data.getBithDay());
        newUser = userRepository.save(newUser);
        //Scopes
        UserScope userScope = new UserScope();
        userScope.setUser(newUser);
        userScope.setScope(scopeRepository.getOne(ID_SCOPE_CREATE_ORDER));
        userScopeRepository.save(userScope);
        return userRepository.findProfileUserById(newUser.getId()).get();
    }
}
