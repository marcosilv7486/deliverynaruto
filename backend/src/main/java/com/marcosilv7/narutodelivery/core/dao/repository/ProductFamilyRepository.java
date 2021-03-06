package com.marcosilv7.narutodelivery.core.dao.repository;

import com.marcosilv7.narutodelivery.core.dao.domain.ProductFamily;
import com.marcosilv7.narutodelivery.core.dto.ProductFamilyDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductFamilyRepository extends JpaRepository<ProductFamily,Long> {

    @Query("select new com.marcosilv7.narutodelivery.core.dto.ProductFamilyDTO (" +
            "p.id,p.name,p.image,(select count(e) from Product e where e.subFamily.family.id =p.id ) ) " +
            "from ProductFamily  p ")
    List<ProductFamilyDTO> findDtoWithConstructorExpression();
}
