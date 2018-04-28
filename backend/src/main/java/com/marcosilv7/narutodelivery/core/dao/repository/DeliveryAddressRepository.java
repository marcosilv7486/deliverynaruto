package com.marcosilv7.narutodelivery.core.dao.repository;

import com.marcosilv7.narutodelivery.core.dao.domain.DeliveryAddress;
import com.marcosilv7.narutodelivery.core.dto.DeliveryAddressDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress,Long> {

    @Query("select new com.marcosilv7.narutodelivery.core.dto.DeliveryAddressDTO (" +
            "d.id," +
            "d.user.id," +
            "d.address," +
            "d.reference," +
            "d.latitude," +
            "d.longitude," +
            "d.favorite" +
            ") from " +
            "DeliveryAddress  d where d.user.id =:userId order by d.favorite desc")
    List<DeliveryAddressDTO> findDtoWithConstructorExpression(@Param("userId") Long userId);

    Optional<DeliveryAddress> findByUserIdAndId(Long userId,Long id);
}
