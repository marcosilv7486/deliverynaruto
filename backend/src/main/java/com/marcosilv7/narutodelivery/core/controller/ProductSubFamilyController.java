package com.marcosilv7.narutodelivery.core.controller;

import com.marcosilv7.narutodelivery.configuration.api.Api;
import com.marcosilv7.narutodelivery.core.dto.ProductFamilyDTO;
import com.marcosilv7.narutodelivery.core.dto.ProductSubFamilyDTO;
import com.marcosilv7.narutodelivery.core.service.interfaces.DeliveryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Api.PRODUCT_SUBFAMILY_PATH)
@io.swagger.annotations.Api(description = "Operaciones sobre las subfamilias",
        consumes="application/json",tags = "SubFamilia de Productos")
public class ProductSubFamilyController  {

    private final DeliveryService deliveryService;

    @Autowired
    public ProductSubFamilyController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping
    @ApiOperation(value = "Obtener Subfamilias", notes = "Obtiene un listado de las subfamilias de los productos."
            ,response = ProductFamilyDTO[].class)
    public List<ProductSubFamilyDTO> getAll(){
        return deliveryService.getAllSubFamilies();
    }
}
