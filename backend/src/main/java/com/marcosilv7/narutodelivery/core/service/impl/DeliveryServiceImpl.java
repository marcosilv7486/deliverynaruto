package com.marcosilv7.narutodelivery.core.service.impl;

import com.marcosilv7.narutodelivery.core.dao.projections.CategoryProductSumaryProjection;
import com.marcosilv7.narutodelivery.core.dao.repository.CategoryProductRepository;
import com.marcosilv7.narutodelivery.core.service.interfaces.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final CategoryProductRepository categoryProductRepository;

    @Autowired
    public DeliveryServiceImpl(CategoryProductRepository categoryProductRepository) {
        this.categoryProductRepository = categoryProductRepository;
    }

    @Override
    public List<CategoryProductSumaryProjection> getAllCategories() {
        return categoryProductRepository.findAllProjectedBy();
    }
}
