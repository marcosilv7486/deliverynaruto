package com.marcosilv7.narutodelivery.configuration.bd;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;


import java.io.IOException;
import java.util.Properties;

@Configuration
public class HibernateProperties {

    public Properties hibernateProperties() {
        Resource resource = new ClassPathResource("hibernate.properties");
        try {
            return PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            return new Properties();
        }
    }
}
