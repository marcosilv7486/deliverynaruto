package com.marcosilv7.narutodelivery.core.service.interfaces;

import com.marcosilv7.narutodelivery.core.dto.ProductDTO;
import com.marcosilv7.narutodelivery.core.dto.ProductFamilyDTO;
import com.marcosilv7.narutodelivery.core.dto.ProductSubFamilyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeliveryService {

    List<ProductFamilyDTO> getAllFamilies();

    List<ProductSubFamilyDTO> getAllSubFamilies();

    Page<ProductDTO> getAllProductsByPageable(Pageable pageable);
}
