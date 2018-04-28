package com.marcosilv7.narutodelivery.core.controller;

import com.marcosilv7.narutodelivery.configuration.api.Api;
import com.marcosilv7.narutodelivery.core.dto.DeliveryAddressDTO;
import com.marcosilv7.narutodelivery.core.service.interfaces.DeliveryService;
import com.marcosilv7.narutodelivery.security.dto.ProfileUserDTO;
import com.marcosilv7.narutodelivery.security.dto.RegisterUserDTO;
import com.marcosilv7.narutodelivery.security.service.interfaces.SecurityService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@io.swagger.annotations.Api(tags = "Usuarios",description = "Operaciones sobre los usuarios",
        consumes="application/json")
@RestController
@RequestMapping(Api.USER_PATH)
public class UserController {

    private final SecurityService securityService;
    private final DeliveryService deliveryService;

    @Autowired
    public UserController(SecurityService securityService, DeliveryService deliveryService) {
        this.securityService = securityService;
        this.deliveryService = deliveryService;
    }

    @ApiOperation(value = "Crear nuevo usuario", notes = "Crea un nuevo usuario segun la informacion enviada. " +
            "Asimismo, el email debe ser unico por usuario.",response = ProfileUserDTO.class)
    @PostMapping()
    public ProfileUserDTO createUser(@Valid @RequestBody RegisterUserDTO data){
        return securityService.createUser(data);
    }

    @GetMapping("/{userId}"+Api.DELIVERY_ADDRESS)
    @ApiOperation(value = "Obtener Direcciones de entrega", notes = "Obtiene un listado de direcciones por usuario."
            ,response = DeliveryAddressDTO[].class)
    public List<DeliveryAddressDTO> getAllByUserId(@PathVariable("userId") Long userId){
        return deliveryService.getDeliveryAddressByUser(userId);
    }

    @PostMapping("/{userId}"+Api.DELIVERY_ADDRESS)
    @ApiOperation(value = "Crear direccion de entrega", notes = "Crea una nueva direccion para el usuario asociado"
            ,response = DeliveryAddressDTO.class)
    public DeliveryAddressDTO createDeliveryAddress(@PathVariable("userId") Long userId,
                                                    @Valid @RequestBody DeliveryAddressDTO data){
        return deliveryService.createDeliveryAddress(userId,data);
    }

    @PutMapping("/{userId}"+Api.DELIVERY_ADDRESS+"/{deliveryAddressId}")
    @ApiOperation(value = "Modicar direccion de entrega", notes = "Modificar una direccion de entrega por usuario e identificador"
            ,response = DeliveryAddressDTO.class)
    public DeliveryAddressDTO updateDeliveryAddress(@PathVariable("userId") Long userId,
                                                    @PathVariable("deliveryAddressId") Long deliveryAddressId,
                                                    @Valid @RequestBody DeliveryAddressDTO data){
        return deliveryService.updateDeliveryAddress(userId,deliveryAddressId,data);
    }

    @DeleteMapping("/{userId}"+Api.DELIVERY_ADDRESS+"/{deliveryAddressId}")
    @ApiOperation(value = "Eliminar direccion de entrega", notes = "Elimina una direccion de entrega por usuario e identificador")
    public void deleteDeliveryAddress(@PathVariable("userId") Long userId,
                                                    @PathVariable("deliveryAddressId") Long deliveryAddressId){
        deliveryService.deleteDeliveryAddress(userId,deliveryAddressId);
    }

}
