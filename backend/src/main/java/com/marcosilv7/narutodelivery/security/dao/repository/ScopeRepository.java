package com.marcosilv7.narutodelivery.security.dao.repository;

import com.marcosilv7.narutodelivery.security.dao.domain.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScopeRepository extends JpaRepository<Scope,Long> {
}
