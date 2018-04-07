package com.marcosilv7.narutodelivery.security.service.interfaces;

import com.marcosilv7.narutodelivery.security.dao.projections.UserSummaryProjection;

import java.util.Optional;

public interface SecurityService {
    Optional<UserSummaryProjection> findByUsername(String username);
}
