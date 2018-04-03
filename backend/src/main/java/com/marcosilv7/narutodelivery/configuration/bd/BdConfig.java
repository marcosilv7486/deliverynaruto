package com.marcosilv7.narutodelivery.configuration.bd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactoryApp",
        transactionManagerRef = "transactionManagerApp",
        basePackages = "com.marcosilv7.narutodelivery.core.dao.repository")
public class BdConfig {

    private final HibernateProperties hibernateProperties;
    private final Environment environment;

    @Autowired
    public BdConfig(HibernateProperties hibernateProperties, Environment environment) {
        this.hibernateProperties = hibernateProperties;
        this.environment = environment;
    }

    @Bean(name = "transactionManagerApp")
    @Primary
    public PlatformTransactionManager transactionManagerApp() {
        return new JpaTransactionManager(entityManagerFactoryApp().getObject());
    }

    @Bean(name = "entityManagerFactoryApp")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryApp() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSourceApp());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        Properties properties = hibernateProperties.hibernateProperties();
        factoryBean.setJpaProperties(properties);
        factoryBean.setPackagesToScan("com.marcosilv7.narutodelivery.core.dao.domain");
        factoryBean.setPersistenceUnitName("app");
        return factoryBean;
    }

    @Primary
    @Bean(name = "dataSourceApp")
    @ConfigurationProperties(prefix = "app")
    public DataSource dataSourceApp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("app.driver-class-name"));
        dataSource.setUrl(environment.getProperty("app.url"));
        dataSource.setUsername(environment.getProperty("app.username"));
        dataSource.setPassword(environment.getProperty("app.password"));

        return dataSource;
    }
}
