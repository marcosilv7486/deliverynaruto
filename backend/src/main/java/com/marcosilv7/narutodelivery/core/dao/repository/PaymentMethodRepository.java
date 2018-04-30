package com.marcosilv7.narutodelivery.core.dao.repository;

import com.marcosilv7.narutodelivery.core.dao.domain.PaymentMethod;
import com.marcosilv7.narutodelivery.core.dto.PaymentMethodDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod,Long>{

    @Query("select new com.marcosilv7.narutodelivery.core.dto.PaymentMethodDTO (" +
            "p.id," +
            "p.user.id," +
            "p.numberCreditCard," +
            "p.monthExp," +
            "p.yearExp," +
            "p.cvv," +
            "p.favorite," +
            "p.postalCode" +
            ") from PaymentMethod p " +
            "where p.user.id =:userId order by p.favorite desc ")
    List<PaymentMethodDTO> findDtoWithConstructorExpression(@Param("userId") Long userId);

    Optional<PaymentMethod> findByUserIdAndId(Long userId, Long id );
}
