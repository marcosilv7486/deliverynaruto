package com.marcosilv7.narutodelivery.core.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcosilv7.narutodelivery.NarutodeliveryApplication;
import com.marcosilv7.narutodelivery.configuration.api.Api;
import com.marcosilv7.narutodelivery.configuration.messages.MessageUtil;
import com.marcosilv7.narutodelivery.configuration.security.WebSecurityConfig;
import com.marcosilv7.narutodelivery.core.dto.DeliveryAddressDTO;
import com.marcosilv7.narutodelivery.core.dto.ProductFamilyDTO;
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

import java.time.LocalDate;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {NarutodeliveryApplication.class,WebSecurityConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;
    @Autowired(required = false)
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityService securityService;
    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private MessageUtil messageUtil;

    private RegisterUserDTO newUser;
    private RegisterUserDTO failUserByEmail;


    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true)
                .alwaysDo(MockMvcResultHandlers.print())
                .addFilters(springSecurityFilterChain).build();
        newUser = new RegisterUserDTO();
        newUser.setBithDay(new Date());
        newUser.setName(RandomStringUtils.randomAlphabetic(10));
        newUser.setLastName(RandomStringUtils.randomAlphabetic(10));
        newUser.setEmail(RandomStringUtils.randomAlphabetic(10)+"@gmail.com");
        newUser.setPhone(RandomStringUtils.randomNumeric(9));
        newUser.setPassword(RandomStringUtils.randomAlphabetic(8));

        failUserByEmail = new RegisterUserDTO();
        failUserByEmail.setBithDay(new Date());
        failUserByEmail.setName(RandomStringUtils.randomAlphabetic(10));
        failUserByEmail.setLastName(RandomStringUtils.randomAlphabetic(10));
        failUserByEmail.setEmail(TestUtil.EMAIL_USER);
        failUserByEmail.setPhone(RandomStringUtils.randomNumeric(9));
        failUserByEmail.setPassword(RandomStringUtils.randomAlphabetic(8));
    }

    @Test
    @Transactional
    public void createUser() throws Exception {
        //Creacion
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(Api.USER_PATH)
                .contentType(WebSecurityConfig.CONTENT_TYPE)
                .header(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,Api.TOKEN_TEST)
                .content(objectMapper.writeValueAsString(newUser))
                .accept(WebSecurityConfig.CONTENT_TYPE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andDo(print()).andReturn();
        ProfileUserDTO respondeDTO = objectMapper.readValue(result.getResponse().getContentAsString(),
                ProfileUserDTO.class);
        Assert.assertNotNull(respondeDTO);
        Assert.assertNotNull(respondeDTO.getId());
        //Que exista en la BD
        Assert.assertEquals(true,securityService.findByUsername(newUser.getEmail()).isPresent());
        //Verificar los campos
        Assert.assertEquals(newUser.getName()+" "+newUser.getLastName(),respondeDTO.getFullName());
        Assert.assertEquals(newUser.getEmail(),respondeDTO.getEmail());
        Assert.assertEquals(newUser.getPhone(),respondeDTO.getPhone());
        //Assert.assertEquals(respondeDTO.getBirthDay().compareTo(newUser.getBithDay()),0);
        //Realizar un Login Correcto
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(newUser.getEmail());
        loginRequest.setPassword(newUser.getPassword());
        mockMvc.perform(MockMvcRequestBuilders.post(Api.LOGIN_PATH)
                .contentType(WebSecurityConfig.CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(loginRequest))
                .accept(WebSecurityConfig.CONTENT_TYPE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andDo(print()).andReturn();
    }

    @Test
    public void createUser_error_correo_repetido() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(Api.USER_PATH)
                .contentType(WebSecurityConfig.CONTENT_TYPE)
                .header(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,Api.TOKEN_TEST)
                .content(objectMapper.writeValueAsString(failUserByEmail))
                .accept(WebSecurityConfig.CONTENT_TYPE))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value(messageUtil.findByCode("El correo ya se encuentra en uso.")))
                .andDo(print());
    }

    @Test
    public void getAllByUserId() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(Api.USER_PATH+"/"+TestUtil.USER_ID+Api.DELIVERY_ADDRESS)
                .contentType(WebSecurityConfig.CONTENT_TYPE)
                .header(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,Api.TOKEN_TEST)
                .accept(WebSecurityConfig.CONTENT_TYPE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andDo(print()).andReturn();
        DeliveryAddressDTO[] response = objectMapper.readValue(result.getResponse().getContentAsString(),
                DeliveryAddressDTO[].class);
        Assert.assertEquals(4,response.length);
        Assert.assertEquals(deliveryService.getDeliveryAddressByUser(TestUtil.USER_ID).size(),response.length);
    }

    @Test
    @Transactional
    public void createDeliveryAddress() throws Exception {
        DeliveryAddressDTO data = new DeliveryAddressDTO();
        data.setAddress("Mi Casita");
        data.setReference("Cerca a mi parque");
        data.setFavorite(true);
        data.setLatitude(0.0);
        data.setLongitude(0.0);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(Api.USER_PATH+"/"+TestUtil.USER_ID+Api.DELIVERY_ADDRESS)
                .contentType(WebSecurityConfig.CONTENT_TYPE)
                .header(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,Api.TOKEN_TEST)
                .content(objectMapper.writeValueAsString(data))
                .accept(WebSecurityConfig.CONTENT_TYPE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andDo(print()).andReturn();
        DeliveryAddressDTO responseDTO = objectMapper.readValue(result.getResponse().getContentAsString(),
                DeliveryAddressDTO.class);
        Assert.assertNotNull(responseDTO);
        Assert.assertNotNull(responseDTO.getId());
        Assert.assertEquals(TestUtil.USER_ID,responseDTO.getUserId());
        Assert.assertEquals(data.getAddress(),responseDTO.getAddress());
        Assert.assertEquals(data.getReference(),responseDTO.getReference());
        Assert.assertEquals(data.getLatitude(),responseDTO.getLatitude());
        Assert.assertEquals(data.getLatitude(),responseDTO.getLongitude());
        Assert.assertEquals(data.getFavorite(),responseDTO.getFavorite());
    }

    @Test
    @Transactional
    public void updateDeliveryAddress() throws Exception{
        Long addressId = 1L;
        DeliveryAddressDTO data = new DeliveryAddressDTO();
        data.setAddress("Casa Grande");
        data.setReference("Jupiter");
        data.setFavorite(false);
        data.setLatitude(-1.0);
        data.setLongitude(-1.0);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(Api.USER_PATH+"/"+TestUtil.USER_ID+Api.DELIVERY_ADDRESS+"/"+addressId)
                .contentType(WebSecurityConfig.CONTENT_TYPE)
                .header(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,Api.TOKEN_TEST)
                .content(objectMapper.writeValueAsString(data))
                .accept(WebSecurityConfig.CONTENT_TYPE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andDo(print()).andReturn();
        DeliveryAddressDTO responseDTO = objectMapper.readValue(result.getResponse().getContentAsString(),
                DeliveryAddressDTO.class);
        Assert.assertNotNull(responseDTO);
        Assert.assertEquals(TestUtil.USER_ID,responseDTO.getUserId());
        Assert.assertEquals(addressId,responseDTO.getId());
        Assert.assertEquals(data.getAddress(),responseDTO.getAddress());
        Assert.assertEquals(data.getReference(),responseDTO.getReference());
        Assert.assertEquals(data.getLatitude(),responseDTO.getLatitude());
        Assert.assertEquals(data.getLatitude(),responseDTO.getLongitude());
        Assert.assertEquals(data.getFavorite(),responseDTO.getFavorite());
    }

    @Test
    @Transactional
    public void deleteDeliveryAddress() throws Exception {
        Long addressId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete(Api.USER_PATH+"/"+TestUtil.USER_ID+Api.DELIVERY_ADDRESS+"/"+addressId)
                .contentType(WebSecurityConfig.CONTENT_TYPE)
                .header(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,Api.TOKEN_TEST)
                .accept(WebSecurityConfig.CONTENT_TYPE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
}
