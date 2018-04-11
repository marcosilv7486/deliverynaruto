package com.marcosilv7.narutodelivery.core.controller;

import com.marcosilv7.narutodelivery.configuration.api.Api;
import com.marcosilv7.narutodelivery.core.dto.ProductFamilyDTO;
import com.marcosilv7.narutodelivery.core.service.interfaces.DeliveryService;
import com.marcosilv7.narutodelivery.security.dto.ProfileUserDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Api.PRODUCT_FAMILY_PATH)
@io.swagger.annotations.Api(description = "Operaciones sobre las familias de los productos",
        consumes="application/json",tags = "Familia de Productos")
public class ProductFamilyController {

    private final DeliveryService deliveryService;

    @Autowired
    public ProductFamilyController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping
    @ApiOperation(value = "Obtener familias", notes = "Obtiene un listado de las familias de los productos."
            ,response = ProductFamilyDTO[].class)
    public List<ProductFamilyDTO> getAll(){
        return deliveryService.getAllFamilies();
    }
}
