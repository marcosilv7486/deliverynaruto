package com.marcosilv7.narutodelivery.security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcosilv7.narutodelivery.NarutodeliveryApplication;
import com.marcosilv7.narutodelivery.configuration.api.Api;
import com.marcosilv7.narutodelivery.configuration.security.WebSecurityConfig;
import com.marcosilv7.narutodelivery.core.dto.ProductSubFamilyDTO;
import com.marcosilv7.narutodelivery.core.service.interfaces.DeliveryService;
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
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {NarutodeliveryApplication.class,WebSecurityConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SubFamilyControllerTest {


    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;
    @Autowired(required = false)
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DeliveryService deliveryService;


    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true)
                .alwaysDo(MockMvcResultHandlers.print())
                .addFilters(springSecurityFilterChain).build();
    }

    @Test
    public void getAllSubFamilies_exitoso() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(Api.PRODUCT_SUBFAMILY_PATH)
                .contentType(WebSecurityConfig.CONTENT_TYPE)
                .header(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,Api.TOKEN_TEST)
                .accept(WebSecurityConfig.CONTENT_TYPE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andDo(print()).andReturn();
        ProductSubFamilyDTO[] response = objectMapper.readValue(result.getResponse().getContentAsString(),
                ProductSubFamilyDTO[].class);
        Assert.assertEquals(16,response.length);
        Assert.assertEquals(deliveryService.getAllSubFamilies().size(),response.length);
    }

}
