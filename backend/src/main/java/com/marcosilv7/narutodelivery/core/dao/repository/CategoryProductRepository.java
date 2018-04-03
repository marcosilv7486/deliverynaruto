package com.marcosilv7.narutodelivery.core.dao.repository;

import com.marcosilv7.narutodelivery.core.dao.domain.CategoryProduct;
import com.marcosilv7.narutodelivery.core.dao.projections.CategoryProductSumaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryProductRepository extends JpaRepository<CategoryProduct,Long> {

    List<CategoryProductSumaryProjection> findAllProjectedBy();
}
