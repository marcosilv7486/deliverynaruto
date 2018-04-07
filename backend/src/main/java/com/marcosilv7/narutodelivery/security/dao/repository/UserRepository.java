package com.marcosilv7.narutodelivery.security.dao.repository;

import com.marcosilv7.narutodelivery.security.dao.domain.User;
import com.marcosilv7.narutodelivery.security.dao.projections.UserSummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<UserSummaryProjection> findByUserName(String username);
}
