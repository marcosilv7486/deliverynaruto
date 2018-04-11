package com.marcosilv7.narutodelivery.core.dao.repository;

import com.marcosilv7.narutodelivery.core.dao.domain.ProductSubFamily;
import com.marcosilv7.narutodelivery.core.dto.ProductSubFamilyDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSubFamilyRepository extends JpaRepository<ProductSubFamily,Long> {

    @Query("select new com.marcosilv7.narutodelivery.core.dto.ProductSubFamilyDTO (" +
            "p.id,p.name,p.family.name) from ProductSubFamily  p ")
    List<ProductSubFamilyDTO> findDtoWithConstructorExpression();
}
