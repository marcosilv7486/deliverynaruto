package com.marcosilv7.narutodelivery.core.service.interfaces;

import com.marcosilv7.narutodelivery.core.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.List;

public interface DeliveryService {

    List<ProductFamilyDTO> getAllFamilies();

    List<ProductSubFamilyDTO> getAllSubFamilies();

    Page<ProductDTO> getAllProductsByPageable(Pageable pageable);

    List<ProductDTO> getProductsByFamily(Long familyId);

    List<DeliveryAddressDTO> getDeliveryAddressByUser(Long userId);

    DeliveryAddressDTO createDeliveryAddress(Long userId,DeliveryAddressDTO data);

    void deleteDeliveryAddress(Long userId, Long deliveryAddressId);

    DeliveryAddressDTO updateDeliveryAddress(Long userId, Long deliveryAddressId, @Valid DeliveryAddressDTO data);

    List<PaymentMethodDTO> getPaymentMethodByUser(Long userId);

    PaymentMethodDTO createPaymentMethod (Long userId,PaymentMethodDTO data);

    PaymentMethodDTO updatePaymentMethod(Long userId,Long id,PaymentMethodDTO data);

    void deletePaymentMethod(Long userId,Long id);

    OrderDTO createOrder(@Valid OrderDTO data);

    List<OrderDTO> getOrdersByStatusAndUserId(String status, Long userId);

    List<OrderDTO> getOrdersByStatus(String status);

    List<OrderDTO> getOrdersByUserId(Long userId);
}
