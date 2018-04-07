package com.marcosilv7.narutodelivery.security.dao.projections;

import org.springframework.beans.factory.annotation.Value;

public interface UserScopeSummaryProjection {

    @Value("#{target.scope.nameSpring}")
    String getScope();

}
