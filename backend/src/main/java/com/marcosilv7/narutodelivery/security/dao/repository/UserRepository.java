package com.marcosilv7.narutodelivery.security.dao.repository;

import com.marcosilv7.narutodelivery.security.dao.domain.User;
import com.marcosilv7.narutodelivery.security.dto.ProfileUserDTO;
import com.marcosilv7.narutodelivery.security.dto.UserDTO;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select new com.marcosilv7.narutodelivery.security.dto.UserDTO(" +
            "u.id,u.userName,u.fullName,u.avatar,u.enabled,u.password,u.lastLogin) from User u " +
            "where u.userName =:userName")
    Optional<UserDTO> findDtoWithConstructorExpression(@Param("userName") String userName);

    @Query("select new com.marcosilv7.narutodelivery.security.dto.ProfileUserDTO(" +
            "u.id,u.userName,u.fullName,u.avatar,u.bithDay,u.phone) from User u " +
            "where u.id =:userId")
    Optional<ProfileUserDTO> findProfileUserById(@Param("userId")Long id);
}
