package com.marcosilv7.narutodelivery.configuration.bd;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan({"com.marcosilv7.narutodelivery.core.dao.domain","com.marcosilv7.narutodelivery.security.dao.domain"})
@EnableJpaRepositories(basePackages = {"com.marcosilv7.narutodelivery.core.dao.repository"
                ,"com.marcosilv7.narutodelivery.security.dao.repository"})
public class BdConfig {

}
