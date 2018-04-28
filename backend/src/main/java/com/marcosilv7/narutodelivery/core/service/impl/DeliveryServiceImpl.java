package com.marcosilv7.narutodelivery.core.service.impl;

import com.marcosilv7.narutodelivery.configuration.exceptions.BusinessException;
import com.marcosilv7.narutodelivery.configuration.exceptions.EntityNotFoundException;
import com.marcosilv7.narutodelivery.core.dao.domain.DeliveryAddress;
import com.marcosilv7.narutodelivery.core.dao.repository.DeliveryAddressRepository;
import com.marcosilv7.narutodelivery.core.dao.repository.ProductFamilyRepository;
import com.marcosilv7.narutodelivery.core.dao.repository.ProductRepository;
import com.marcosilv7.narutodelivery.core.dao.repository.ProductSubFamilyRepository;
import com.marcosilv7.narutodelivery.core.dto.DeliveryAddressDTO;
import com.marcosilv7.narutodelivery.core.dto.ProductDTO;
import com.marcosilv7.narutodelivery.core.dto.ProductFamilyDTO;
import com.marcosilv7.narutodelivery.core.dto.ProductSubFamilyDTO;
import com.marcosilv7.narutodelivery.core.service.interfaces.DeliveryService;
import com.marcosilv7.narutodelivery.security.dao.domain.User;
import com.marcosilv7.narutodelivery.security.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
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

    @Autowired
    public DeliveryServiceImpl(ProductFamilyRepository productFamilyRepository,
                               ProductSubFamilyRepository productSubFamilyRepository,
                               ProductRepository productRepository,
                               DeliveryAddressRepository deliveryAddressRepository,
                               UserRepository userRepository) {
        this.productFamilyRepository = productFamilyRepository;
        this.productSubFamilyRepository = productSubFamilyRepository;
        this.productRepository = productRepository;
        this.deliveryAddressRepository = deliveryAddressRepository;
        this.userRepository = userRepository;
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
        entity.setReference(data.getReference());
        entity.setCreatedAt(new Date());
        entity = deliveryAddressRepository.save(entity);
        return new DeliveryAddressDTO(entity.getId(),
                entity.getUser().getId(),entity.getAddress(),
                entity.getReference(),entity.getLatitude(),
                entity.getLongitude(),entity.getFavorite());
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
        deliveryAddress.setReference(data.getReference());
        deliveryAddress.setAddress(data.getAddress());
        deliveryAddress.setLongitude(data.getLongitude());
        deliveryAddress.setLatitude(data.getLatitude());
        deliveryAddress.setFavorite(data.getFavorite());
        deliveryAddress = deliveryAddressRepository.save(deliveryAddress);
        return new DeliveryAddressDTO(deliveryAddress.getId(),
                deliveryAddress.getUser().getId(),deliveryAddress.getAddress(),
                deliveryAddress.getReference(),deliveryAddress.getLatitude(),
                deliveryAddress.getLongitude(),deliveryAddress.getFavorite());
    }
}
