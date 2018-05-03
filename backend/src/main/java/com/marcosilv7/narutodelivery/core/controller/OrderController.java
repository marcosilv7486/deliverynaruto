package com.marcosilv7.narutodelivery.core.controller;

import com.marcosilv7.narutodelivery.configuration.api.Api;
import com.marcosilv7.narutodelivery.core.dto.OrderDTO;
import com.marcosilv7.narutodelivery.core.dto.ProductDTO;
import com.marcosilv7.narutodelivery.core.service.interfaces.DeliveryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Api.ORDER_PATH)
@io.swagger.annotations.Api(description = "Operaciones sobre las ordenes de pedido", consumes="application/json",tags = "Ordenes de Pedido")
public class OrderController {

    private final DeliveryService deliveryService;

    @Autowired
    public OrderController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping
    @ApiOperation(value = "Crear Orden de Pedido", notes = "Crear una nueva orden de pedido."
            ,response = OrderDTO.class)
    public OrderDTO createOrder(@Valid @RequestBody OrderDTO data){
        return deliveryService.createOrder(data);
    }
}
