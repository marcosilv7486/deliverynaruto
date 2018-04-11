package com.marcosilv7.narutodelivery.security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcosilv7.narutodelivery.NarutodeliveryApplication;
import com.marcosilv7.narutodelivery.configuration.api.Api;
import com.marcosilv7.narutodelivery.configuration.messages.MessageUtil;
import com.marcosilv7.narutodelivery.configuration.security.WebSecurityConfig;
import com.marcosilv7.narutodelivery.security.authentication.login.LoginRequest;
import com.marcosilv7.narutodelivery.security.dto.UserDTO;
import com.marcosilv7.narutodelivery.security.service.interfaces.SecurityService;
import com.marcosilv7.narutodelivery.util.TestUtil;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {NarutodeliveryApplication.class,WebSecurityConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthenticationControllerTest {


    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;
    @Autowired(required = false)
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityService securityService;

    private UserDTO userDTOValido;
    private UserDTO userDTOBloqueado;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true)
                .addFilters(springSecurityFilterChain).build();
        userDTOValido = securityService.findByUsername(TestUtil.EMAIL_USER).get();
        userDTOBloqueado = securityService.findByUsername(TestUtil.EMAIL_USER_BLOCKED).get();
    }

    @Test
    public void login_correcto() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(userDTOValido.getUsername());
        loginRequest.setPassword(TestUtil.PASSWORD_USER);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(Api.LOGIN_PATH)
                .contentType(WebSecurityConfig.CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(loginRequest))
                .accept(WebSecurityConfig.CONTENT_TYPE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.data.token").isNotEmpty())
                .andExpect(jsonPath("$.data.userId").value(userDTOValido.getId().toString()))
                .andExpect(jsonPath("$.data.refreshToken").isNotEmpty())
                .andExpect(jsonPath("$.data.email").value(userDTOValido.getUsername()))
                .andExpect(jsonPath("$.data.fullname").value(userDTOValido.getFullName()))
                .andExpect(jsonPath("$.data.avatar").value(userDTOValido.getAvatar()))
                .andDo(print()).andReturn();
        //Obtener el access token
        Map response = objectMapper.readValue(result.getResponse().getContentAsString(),Map.class);
        Map data = (Map)response.get("data");
        mockMvc.perform(MockMvcRequestBuilders.get(Api.PROFILE_PATH+"/"+data.get("userId"))
                .contentType(WebSecurityConfig.CONTENT_TYPE)
                .header(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,"Beaber "+data.get("token"))
                .content(objectMapper.writeValueAsString(loginRequest))
                .accept(WebSecurityConfig.CONTENT_TYPE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }


    @Test
    public void login_credenciales_incorrectos() throws Exception{
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(userDTOValido.getUsername());
        loginRequest.setPassword("pwd");
        mockMvc.perform(MockMvcRequestBuilders.post(Api.LOGIN_PATH)
                .contentType(WebSecurityConfig.CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(loginRequest))
                .accept(WebSecurityConfig.CONTENT_TYPE))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value(messageUtil.findByCode("seguridad.error.credenciales.incorrectos")))
                .andDo(print());
    }

    @Test
    public void login_usuario_bloqueado() throws Exception{
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(userDTOBloqueado.getUsername());
        loginRequest.setPassword(TestUtil.PASSWORD_USER);
        mockMvc.perform(MockMvcRequestBuilders.post(Api.LOGIN_PATH)
                .contentType(WebSecurityConfig.CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(loginRequest))
                .accept(WebSecurityConfig.CONTENT_TYPE))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value(messageUtil.findByCode("seguridad.error.usuario.deshabilitado")))
                .andDo(print());
    }



}
