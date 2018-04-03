package com.marcosilv7.narutodelivery.core.dao.repository;

import com.marcosilv7.narutodelivery.core.dao.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
