package com.marcosilv7.narutodelivery.core.dao.repository;

import com.marcosilv7.narutodelivery.core.dao.domain.OrderDetail;
import com.marcosilv7.narutodelivery.core.dto.OrderDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {

    @Query("select new com.marcosilv7.narutodelivery.core.dto.OrderDetailDTO (" +
            "od.id,od.item,od.description,od.quantity," +
            "od.unitPrice,od.total,od.descriptionImage," +
            "od.product.id,od.order.id" +
            ") from OrderDetail od " +
            "where od.order.id =:orderId order by od.item asc ")
    List<OrderDetailDTO> findByOrderId(@Param("orderId") Long orderId);
}
