package com.marcosilv7.narutodelivery.core.service.impl;

import com.marcosilv7.narutodelivery.configuration.exceptions.BusinessException;
import com.marcosilv7.narutodelivery.configuration.exceptions.EntityNotFoundException;
import com.marcosilv7.narutodelivery.core.dao.domain.*;
import com.marcosilv7.narutodelivery.core.dao.repository.*;
import com.marcosilv7.narutodelivery.core.dto.*;
import com.marcosilv7.narutodelivery.core.service.interfaces.DeliveryService;
import com.marcosilv7.narutodelivery.security.dao.domain.User;
import com.marcosilv7.narutodelivery.security.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final ProductFamilyRepository productFamilyRepository;
    private final ProductSubFamilyRepository productSubFamilyRepository;
    private final ProductRepository productRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final UserRepository userRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;


    @Autowired
    public DeliveryServiceImpl(ProductFamilyRepository productFamilyRepository,
                               ProductSubFamilyRepository productSubFamilyRepository,
                               ProductRepository productRepository,
                               DeliveryAddressRepository deliveryAddressRepository,
                               UserRepository userRepository, PaymentMethodRepository paymentMethodRepository, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.productFamilyRepository = productFamilyRepository;
        this.productSubFamilyRepository = productSubFamilyRepository;
        this.productRepository = productRepository;
        this.deliveryAddressRepository = deliveryAddressRepository;
        this.userRepository = userRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public List<ProductFamilyDTO> getAllFamilies() {
        return productFamilyRepository.findDtoWithConstructorExpression();
    }

    @Override
    public List<ProductSubFamilyDTO> getAllSubFamilies() {
        return productSubFamilyRepository.findDtoWithConstructorExpression();
    }

    @Override
    public Page<ProductDTO> getAllProductsByPageable(Pageable pageable) {
        return productRepository.findDtoWithConstructorExpression(pageable);
    }

    @Override
    public List<ProductDTO> getProductsByFamily(Long familyId) {
        return productRepository.findDtoWithConstructorExpression(familyId);
    }

    @Override
    public List<DeliveryAddressDTO> getDeliveryAddressByUser(Long userId) {
        return deliveryAddressRepository.findDtoWithConstructorExpression(userId);
    }

    @Override
    @Transactional
    public DeliveryAddressDTO createDeliveryAddress(Long userId,DeliveryAddressDTO data) {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()){
            throw new BusinessException("No se encontro el usuario con id: "+userId);
        }
        DeliveryAddress entity = new DeliveryAddress();
        entity.setUser(user.get());
        entity.setAddress(data.getAddress());
        entity.setFavorite(data.getFavorite());
        entity.setLatitude(data.getLatitude());
        entity.setLongitude(data.getLongitude());
        entity.setAlias(data.getAlias());
        entity.setPhone(data.getPhone());
        entity.setCreatedAt(new Date());
        entity = deliveryAddressRepository.save(entity);
        return new DeliveryAddressDTO(entity.getId(),
                entity.getUser().getId(),entity.getAddress(),
                entity.getAlias(),entity.getLatitude(),
                entity.getLongitude(),entity.getFavorite(),entity.getPhone());
    }

    @Override
    @Transactional
    public void deleteDeliveryAddress(Long userId, Long deliveryAddressId) {
        Optional<DeliveryAddress> entityOpt = deliveryAddressRepository.findByUserIdAndId(userId,deliveryAddressId);
        if(!entityOpt.isPresent()){
            throw new EntityNotFoundException("No se encontro la direccion con id: "+deliveryAddressId);
        }
        DeliveryAddress deliveryAddress = entityOpt.get();
        deliveryAddress.setDeletedAt(new Date());
        deliveryAddressRepository.save(deliveryAddress);
    }

    @Override
    @Transactional
    public DeliveryAddressDTO updateDeliveryAddress(Long userId, Long deliveryAddressId, @Valid DeliveryAddressDTO data) {
        Optional<DeliveryAddress> entityOpt = deliveryAddressRepository.findByUserIdAndId(userId,deliveryAddressId);
        if(!entityOpt.isPresent()){
            throw new EntityNotFoundException("No se encontro la direccion con id: "+deliveryAddressId);
        }
        DeliveryAddress deliveryAddress = entityOpt.get();
        deliveryAddress.setUpdatedAt(new Date());
        deliveryAddress.setAlias(data.getAlias());
        deliveryAddress.setAddress(data.getAddress());
        deliveryAddress.setLongitude(data.getLongitude());
        deliveryAddress.setLatitude(data.getLatitude());
        deliveryAddress.setFavorite(data.getFavorite());
        deliveryAddress.setPhone(data.getPhone());
        deliveryAddress = deliveryAddressRepository.save(deliveryAddress);
        return new DeliveryAddressDTO(deliveryAddress.getId(),
                deliveryAddress.getUser().getId(),deliveryAddress.getAddress(),
                deliveryAddress.getAlias(),deliveryAddress.getLatitude(),
                deliveryAddress.getLongitude(),deliveryAddress.getFavorite(),deliveryAddress.getPhone());
    }

    @Override
    public List<PaymentMethodDTO> getPaymentMethodByUser(Long userId) {
        return paymentMethodRepository.findDtoWithConstructorExpression(userId);
    }

    @Override
    @Transactional
    public PaymentMethodDTO createPaymentMethod(Long userId, PaymentMethodDTO data) {
        Optional<User> userOpt = userRepository.findById(userId);
        if(!userOpt.isPresent()){
            throw new BusinessException("No se encontro el usuario con id: "+userId);
        }
        PaymentMethod entity = new PaymentMethod();
        entity.setCreatedAt(new Date());
        entity.setCvv(data.getCvv());
        entity.setFavorite(true);
        entity.setMonthExp(data.getMonthExp());
        entity.setPostalCode(data.getPostalCode());
        entity.setNumberCreditCard(data.getNumberCreditCard());
        entity.setYearExp(data.getYearExp());
        entity.setUser(userOpt.get());
        entity = paymentMethodRepository.save(entity);
        return new PaymentMethodDTO(entity.getId(),entity.getUser().getId(),
                entity.getNumberCreditCard(), entity.getMonthExp(),entity.getYearExp(),
                entity.getCvv(),entity.getFavorite(), entity.getPostalCode());
    }

    @Override
    @Transactional
    public PaymentMethodDTO updatePaymentMethod(Long userId, Long id, PaymentMethodDTO data) {
        Optional<PaymentMethod> entityOpt = paymentMethodRepository.findByUserIdAndId(userId,id);
        if(!entityOpt.isPresent()){
            throw new EntityNotFoundException("No se encontro el metodo de pago con id: "+id);
        }
        PaymentMethod entity = entityOpt.get();
        entity.setUpdatedAt(new Date());
        entity = paymentMethodRepository.save(entity);
        return new PaymentMethodDTO(entity.getId(),entity.getUser().getId(),
                entity.getNumberCreditCard(), entity.getMonthExp(),entity.getYearExp(),
                entity.getCvv(),entity.getFavorite(), entity.getPostalCode());
    }

    @Override
    @Transactional
    public void deletePaymentMethod(Long userId, Long id) {
        Optional<PaymentMethod> entityOpt = paymentMethodRepository.findByUserIdAndId(userId,id);
        if(!entityOpt.isPresent()){
            throw new EntityNotFoundException("No se encontro el metodo de pago con id: "+id);
        }
        PaymentMethod entity = entityOpt.get();
        entity.setDeletedAt(new Date());
        paymentMethodRepository.save(entity);
    }

    @Override
    @Transactional
    public OrderDTO createOrder(@Valid OrderDTO data) {
        Optional<User> userOpt = userRepository.findById(data.getUserId());
        if(!userOpt.isPresent()){
            throw new EntityNotFoundException("No se encontro el usuario con id : "+data.getUserId());
        }
        Order order = new Order();
        //User
        order.setUser(userOpt.get());
        order.setUserFullName(userOpt.get().getFullName());
        order.setUserPhone(userOpt.get().getPhone());
        //Fechas
        order.setCreatedAt(new Date());
        order.setArrivalDate(new Date());
        order.setShippingDate(new Date());
        //Direccion
        order.setLatUserAddress(data.getLatUserAddress());
        order.setLonUserAddress(data.getLonUserAddress());
        order.setUserAddress(data.getUserAddress());
        //Estado
        order.setStatus("ENVIADO");
        //Extras
        order.setNumberCreditCard(data.getNumberCreditCard());
        order.setNumber(new Date().getTime()+"");
        order.setInvoiceType(data.getInvoiceType());
        order.setPaymentType(data.getPaymentType());
        order.setRucNumber(data.getRucNumber());
        order.setRazonSocial(data.getRazonSocial());
        order.setNombreComercial(data.getNombreComercial());
        order.setDomicilioFiscal(data.getDomicilioFiscal());
        order.setTotal(BigDecimal.ZERO);
        //Detalle
        int numberItem=1;
        for(OrderDetailDTO itemDTO : data.getItems()){
            OrderDetail item = new OrderDetail();
            item.setOrder(order);
            item.setItem(numberItem);
            item.setQuantity(itemDTO.getQuantity());
            Optional<Product> productOpt = productRepository.findById(itemDTO.getProductId());
            if(!productOpt.isPresent()){
                throw new EntityNotFoundException("No se encontro el producto con id : "+itemDTO.getProductId());
            }
            item.setProduct(productOpt.get());
            item.setDescription(productOpt.get().getName());
            item.setDescriptionImage(productOpt.get().getImage());
            item.setUnitPrice(productOpt.get().getPrice());
            item.setTotal(new BigDecimal(item.getQuantity()).multiply(item.getUnitPrice()));
            order.getDetails().add(item);
            order.setTotal(order.getTotal().add(item.getTotal()));
            numberItem++;
        }
        order = orderRepository.save(order);
        Optional<OrderDTO> orderCreated = orderRepository.findByOrderId(order.getId());
        if(orderCreated.isPresent()){
            orderCreated.get().setItems(orderDetailRepository.findByOrderId(order.getId()));
        }
        return orderCreated.orElse(null);
    }

    @Override
    public List<OrderDTO> getOrdersByStatusAndUserId(String status, Long userId) {
        List<OrderDTO> data = orderRepository.findByStatusAndUserId(status,userId);
        for(OrderDTO item : data){
            item.setItems(orderDetailRepository.findByOrderId(item.getId()));
        }
        return data;
    }

    @Override
    public List<OrderDTO> getOrdersByStatus(String status) {
        List<OrderDTO> data = orderRepository.findByStatus(status);
        for(OrderDTO item : data){
            item.setItems(orderDetailRepository.findByOrderId(item.getId()));
        }
        return data;
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        List<OrderDTO> data = orderRepository.findByUserId(userId);
        for(OrderDTO item : data){
            item.setItems(orderDetailRepository.findByOrderId(item.getId()));
        }
        return data;
    }
}
