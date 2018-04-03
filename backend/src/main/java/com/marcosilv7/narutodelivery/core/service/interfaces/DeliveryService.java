package com.marcosilv7.narutodelivery.core.service.interfaces;

import com.marcosilv7.narutodelivery.core.dao.projections.CategoryProductSumaryProjection;

import java.util.List;

public interface DeliveryService {
    List<CategoryProductSumaryProjection> getAllCategories();
}
