package com.marcosilv7.narutodelivery.core.dao.projections;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface ProductSumaryProjection {
    Long getId();
    String getName();
    String getDescription();
    String getImage();
    @Value("#{target.category.name}")
    String getCategory();
    BigDecimal getPrice();
}
