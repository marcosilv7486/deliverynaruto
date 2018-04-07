package com.marcosilv7.narutodelivery.security.dao.repository;

import com.marcosilv7.narutodelivery.security.dao.domain.UserScope;
import com.marcosilv7.narutodelivery.security.dto.UserScopeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserScopeRepository extends JpaRepository<UserScope,Long>{

    @Query("select new com.marcosilv7.narutodelivery.security.dto.UserScopeDTO (" +
            "us.scope.nameSpring) from UserScope us " +
            "where us.user.id =:userId")
    List<UserScopeDTO> findDtoWithConstructorExpression(@Param("userId") Long userId);
}
