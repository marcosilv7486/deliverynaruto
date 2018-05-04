package com.marcosilv7.narutodelivery.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcosilv7.narutodelivery.NarutodeliveryApplication;
import com.marcosilv7.narutodelivery.configuration.api.Api;
import com.marcosilv7.narutodelivery.configuration.messages.MessageUtil;
import com.marcosilv7.narutodelivery.configuration.security.WebSecurityConfig;
import com.marcosilv7.narutodelivery.core.dao.repository.OrderRepository;
import com.marcosilv7.narutodelivery.core.dto.DeliveryAddressDTO;
import com.marcosilv7.narutodelivery.core.dto.OrderDTO;
import com.marcosilv7.narutodelivery.core.dto.OrderDetailDTO;
import com.marcosilv7.narutodelivery.core.service.interfaces.DeliveryService;
import com.marcosilv7.narutodelivery.security.authentication.login.LoginRequest;
import com.marcosilv7.narutodelivery.security.dto.ProfileUserDTO;
import com.marcosilv7.narutodelivery.security.dto.RegisterUserDTO;
import com.marcosilv7.narutodelivery.security.service.interfaces.SecurityService;
import com.marcosilv7.narutodelivery.util.TestUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import sun.awt.geom.AreaOp;

import java.math.BigDecimal;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {NarutodeliveryApplication.class,WebSecurityConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;
    @Autowired(required = false)
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true)
                .alwaysDo(MockMvcResultHandlers.print())
                .addFilters(springSecurityFilterChain).build();
    }

    @Test
    @Transactional
    public void createOrder() throws Exception {
        long cantidadInicial = orderRepository.count();
        //DATA
        OrderDTO cabecera = new OrderDTO();
        cabecera.setUserId(TestUtil.USER_ID);
        cabecera.setDomicilioFiscal("Mi Domicilio Fiscal");
        cabecera.setNombreComercial("Mi Nombre Comercial");
        cabecera.setRazonSocial("Mi Razon Social");
        cabecera.setRucNumber("11122255567");
        cabecera.setInvoiceType("Factura");
        cabecera.setNumberCreditCard("XXX-XXXX-XXXX-4444");
        cabecera.setLatUserAddress(-1.0);
        cabecera.setLonUserAddress(-1.0);
        cabecera.setPaymentType("Efectivo");
        cabecera.setUserAddress("Mi Direccion de Entrega");
        cabecera.setTotal(BigDecimal.ZERO);
        OrderDetailDTO detalle1 = new OrderDetailDTO();
        detalle1.setQuantity(10);
        detalle1.setProductId(1L);

        OrderDetailDTO detalle2 = new OrderDetailDTO();
        detalle2.setQuantity(10);
        detalle2.setProductId(2L);

        OrderDetailDTO detalle3 = new OrderDetailDTO();
        detalle3.setQuantity(10);
        detalle3.setProductId(3L);

        cabecera.getItems().add(detalle1);
        cabecera.getItems().add(detalle2);
        cabecera.getItems().add(detalle3);

        //Creacion
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(Api.ORDER_PATH)
                .contentType(WebSecurityConfig.CONTENT_TYPE)
                .header(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,Api.TOKEN_TEST)
                .content(objectMapper.writeValueAsString(cabecera))
                .accept(WebSecurityConfig.CONTENT_TYPE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andDo(print()).andReturn();
        OrderDTO respondeDTO = objectMapper.readValue(result.getResponse().getContentAsString(),
                OrderDTO.class);
        Assert.assertEquals(cantidadInicial+1L,orderRepository.count());
        Assert.assertNotNull(respondeDTO);
        Assert.assertNotNull(respondeDTO.getId());
        //Datos del Usuario
        Assert.assertEquals(cabecera.getUserId(),respondeDTO.getUserId());
        //Direccion
        Assert.assertEquals(cabecera.getUserAddress(),respondeDTO.getUserAddress());
        Assert.assertEquals(cabecera.getLatUserAddress(),respondeDTO.getLatUserAddress());
        Assert.assertEquals(cabecera.getLonUserAddress(),respondeDTO.getLonUserAddress());
        //Extras
        Assert.assertEquals(cabecera.getPaymentType(),respondeDTO.getPaymentType());
        Assert.assertEquals(cabecera.getNumberCreditCard(),respondeDTO.getNumberCreditCard());
        Assert.assertEquals(cabecera.getInvoiceType(),respondeDTO.getInvoiceType());
        Assert.assertEquals(cabecera.getRucNumber(),respondeDTO.getRucNumber());
        Assert.assertEquals(cabecera.getRazonSocial(),respondeDTO.getRazonSocial());
        Assert.assertEquals(cabecera.getNombreComercial(),respondeDTO.getNombreComercial());
        Assert.assertEquals(cabecera.getDomicilioFiscal(),respondeDTO.getDomicilioFiscal());
        //Detalle
        Assert.assertEquals(cabecera.getItems().size(),respondeDTO.getItems().size());
    }

    

}
