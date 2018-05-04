package com.marcosilv7.narutodelivery.core.dao.repository;

import com.marcosilv7.narutodelivery.core.dao.domain.Order;
import com.marcosilv7.narutodelivery.core.dto.OrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>{

    @Query("select new com.marcosilv7.narutodelivery.core.dto.OrderDTO(" +
            "o.id,o.user.id,o.number,o.userFullName,o.userPhone," +
            "o.userAddress,o.latUserAddress,o.lonUserAddress," +
            "o.paymentType,o.numberCreditCard,o.invoiceType," +
            "o.rucNumber,o.razonSocial,o.nombreComercial,o.domicilioFiscal," +
            "o.total,o.status," +
            "o.shippingDate,o.arrivalDate" +
            ")" +
            "from Order o where o.id =:id ")
    Optional<OrderDTO> findByOrderId(@Param("id") Long id);

    @Query("select new com.marcosilv7.narutodelivery.core.dto.OrderDTO(" +
            "o.id,o.user.id,o.number,o.userFullName,o.userPhone," +
            "o.userAddress,o.latUserAddress,o.lonUserAddress," +
            "o.paymentType,o.numberCreditCard,o.invoiceType," +
            "o.rucNumber,o.razonSocial,o.nombreComercial,o.domicilioFiscal," +
            "o.total,o.status," +
            "o.shippingDate,o.arrivalDate" +
            ")" +
            "from Order o where o.user.id =:userId order by o.createdAt desc ")
    List<OrderDTO> findByUserId(@Param("userId") Long userId);

    @Query("select new com.marcosilv7.narutodelivery.core.dto.OrderDTO(" +
            "o.id,o.user.id,o.number,o.userFullName,o.userPhone," +
            "o.userAddress,o.latUserAddress,o.lonUserAddress," +
            "o.paymentType,o.numberCreditCard,o.invoiceType," +
            "o.rucNumber,o.razonSocial,o.nombreComercial,o.domicilioFiscal," +
            "o.total,o.status," +
            "o.shippingDate,o.arrivalDate" +
            ")" +
            "from Order o where o.status =:status order by o.createdAt desc ")
    List<OrderDTO> findByStatus(@Param("status") String status);


    @Query("select new com.marcosilv7.narutodelivery.core.dto.OrderDTO(" +
            "o.id,o.user.id,o.number,o.userFullName,o.userPhone," +
            "o.userAddress,o.latUserAddress,o.lonUserAddress," +
            "o.paymentType,o.numberCreditCard,o.invoiceType," +
            "o.rucNumber,o.razonSocial,o.nombreComercial,o.domicilioFiscal," +
            "o.total,o.status," +
            "o.shippingDate,o.arrivalDate" +
            ")" +
            "from Order o where o.status =:status and o.user.id =:userId order by o.createdAt desc ")
    List<OrderDTO> findByStatusAndUserId(@Param("status") String status,@Param("userId") Long userId);
}
