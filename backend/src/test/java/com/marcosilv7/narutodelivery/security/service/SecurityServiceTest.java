package com.marcosilv7.narutodelivery.security.service;

import com.marcosilv7.narutodelivery.NarutodeliveryApplication;
import com.marcosilv7.narutodelivery.configuration.exceptions.BusinessException;
import com.marcosilv7.narutodelivery.configuration.security.WebSecurityConfig;
import com.marcosilv7.narutodelivery.security.dto.ProfileUserDTO;
import com.marcosilv7.narutodelivery.security.dto.RegisterUserDTO;
import com.marcosilv7.narutodelivery.security.service.interfaces.SecurityService;
import com.marcosilv7.narutodelivery.util.TestUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {NarutodeliveryApplication.class,WebSecurityConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SecurityServiceTest {

    @Autowired
    private SecurityService securityService;

    @Test
    @Transactional
    public void createUser_exitoso(){
        RegisterUserDTO newUser = new RegisterUserDTO();
        newUser.setBithDay(LocalDate.now());
        newUser.setName(RandomStringUtils.randomAlphabetic(10));
        newUser.setLastName(RandomStringUtils.randomAlphabetic(10));
        newUser.setEmail(RandomStringUtils.randomAlphabetic(10)+"@gmail.com");
        newUser.setPhone(RandomStringUtils.randomNumeric(9));
        newUser.setPassword(RandomStringUtils.randomAlphabetic(8));
        ProfileUserDTO profileUserDTO = securityService.createUser(newUser);
        Assert.assertNotNull(profileUserDTO);
        Assert.assertNotNull(profileUserDTO.getId());
        //Que exista en la BD
        Assert.assertEquals(securityService.findByUsername(newUser.getEmail()).isPresent(),true);
        //Verificar los campos
        Assert.assertEquals(newUser.getName()+" "+newUser.getLastName(),profileUserDTO.getFullName());
        Assert.assertEquals(newUser.getEmail(),profileUserDTO.getEmail());
        Assert.assertEquals(newUser.getPhone(),profileUserDTO.getPhone());
        Assert.assertEquals(0,profileUserDTO.getBirthDay().compareTo(newUser.getBithDay()));
    }

    @Test(expected = BusinessException.class)
    public void createUser_con_error_correo_repetido(){
        RegisterUserDTO newUser = new RegisterUserDTO();
        newUser.setBithDay(LocalDate.now());
        newUser.setName(RandomStringUtils.randomAlphabetic(10));
        newUser.setLastName(RandomStringUtils.randomAlphabetic(10));
        newUser.setEmail(TestUtil.EMAIL_USER);
        newUser.setPhone(RandomStringUtils.randomNumeric(9));
        newUser.setPassword(RandomStringUtils.randomAlphabetic(8));
        securityService.createUser(newUser);
    }
}
