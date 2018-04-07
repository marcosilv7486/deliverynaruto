package com.marcosilv7.narutodelivery.security.dao.projections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

public interface UserSummaryProjection {

    Long getId();
    String getUsername();
    String getFullName();
    Date getLastLogin();
    @Value("#{target.enabled}")
    boolean isEnabled();
    String getAvatar();
    @JsonIgnore
    String getPassword();
    String getPhone();
    @JsonIgnore
    List<UserScopeSummaryProjection> getScopes();
}
