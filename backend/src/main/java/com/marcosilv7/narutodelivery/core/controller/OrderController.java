package com.marcosilv7.narutodelivery.core.controller;

import com.marcosilv7.narutodelivery.configuration.api.Api;
import com.marcosilv7.narutodelivery.core.dto.OrderDTO;
import com.marcosilv7.narutodelivery.core.service.interfaces.DeliveryService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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

    @PostMapping
    @ApiOperation(value = "Listado de Ordenes de Pedido Por Usuario", notes = "Crear una nueva orden de pedido."
            ,response = OrderDTO.class)
    public List<OrderDTO> getByStatusAndUserId(@RequestParam(value = "status",required = false) String status,
                                               @RequestParam(value = "userId",required = false) Long userId){
        List<OrderDTO> data = new ArrayList<>();
        if(!StringUtils.isEmpty(status) && userId!=null){
            data = deliveryService.getOrdersByStatusAndUserId(status,userId);
        }else {
            if(!StringUtils.isEmpty(status)){
                data = deliveryService.getOrdersByStatus(status);
            }
            if(userId!=null){
                data = deliveryService.getOrdersByUserId(userId);
            }
        }
        return data;
    }
}
