package com.marcosilv7.narutodelivery.core.service.impl;

import com.marcosilv7.narutodelivery.core.dao.repository.ProductFamilyRepository;
import com.marcosilv7.narutodelivery.core.dao.repository.ProductRepository;
import com.marcosilv7.narutodelivery.core.dao.repository.ProductSubFamilyRepository;
import com.marcosilv7.narutodelivery.core.dto.ProductDTO;
import com.marcosilv7.narutodelivery.core.dto.ProductFamilyDTO;
import com.marcosilv7.narutodelivery.core.dto.ProductSubFamilyDTO;
import com.marcosilv7.narutodelivery.core.service.interfaces.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final ProductFamilyRepository productFamilyRepository;
    private final ProductSubFamilyRepository productSubFamilyRepository;
    private final ProductRepository productRepository;

    @Autowired
    public DeliveryServiceImpl(ProductFamilyRepository productFamilyRepository,
                               ProductSubFamilyRepository productSubFamilyRepository,
                               ProductRepository productRepository) {
        this.productFamilyRepository = productFamilyRepository;
        this.productSubFamilyRepository = productSubFamilyRepository;
        this.productRepository = productRepository;
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
}
