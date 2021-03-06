package com.marcosilv7.narutodelivery.core.dao.repository;

import com.marcosilv7.narutodelivery.core.dao.domain.Product;
import com.marcosilv7.narutodelivery.core.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("select new com.marcosilv7.narutodelivery.core.dto.ProductDTO (" +
            "p.id,p.name,p.description,p.image,p.price,p.subFamily.family.name,p.subFamily.name) from Product  p ")
    Page<ProductDTO> findDtoWithConstructorExpression(Pageable pageable);

    @Query("select new com.marcosilv7.narutodelivery.core.dto.ProductDTO (" +
            "p.id,p.name,p.description,p.image,p.price,p.subFamily.family.name,p.subFamily.name) from Product " +
            " p where p.subFamily.family.id =:familyId")
    List<ProductDTO> findDtoWithConstructorExpression(@Param("familyId") Long familyId);
}
