package com.marcosilv7.narutodelivery.core.controller;

import com.marcosilv7.narutodelivery.configuration.api.Api;
import com.marcosilv7.narutodelivery.core.dto.ProductDTO;
import com.marcosilv7.narutodelivery.core.dto.ProductFamilyDTO;
import com.marcosilv7.narutodelivery.core.service.interfaces.DeliveryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Api.PRODUCT_PATH)
@io.swagger.annotations.Api(description = "Operaciones sobre los productos", consumes="application/json",tags = "Productos")
public class ProductController {

    private final DeliveryService deliveryService;

    @Autowired
    public ProductController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping
    @ApiOperation(value = "Obtener Productos", notes = "Obtiene un listado de productos con paginacion."
            ,response = Page.class)
    public Page<ProductDTO> getAllByPagination(Pageable pageable){
        return deliveryService.getAllProductsByPageable(pageable);
    }
}
