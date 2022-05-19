package com.epam.esm.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Class {@code DatabaseTestConfiguration} contains the spring database configuration for the tests.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */

@Configuration
@ComponentScan(basePackages = "com.epam.esm")
@PropertySource("classpath:application.properties")
@Profile("test")
public class DatabaseTestConfiguration {

    /**
     * This method creates a component that will be used as a data source.
     *
     * @return the ComboPooledDataSource
     */
    @SneakyThrows
    @Bean
    public DataSource dataSource(@Value("${spring.datasource.driver}") String driver,
                                 @Value("${spring.datasource.url}") String url,
                                 @Value("${spring.datasource.user}") String user,
                                 @Value("${spring.datasource.password}") String password,
                                 @Value("${spring.datasource.poolSize}") int poolSize) {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        dataSource.setDriverClass(driver);
        dataSource.setJdbcUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setMinPoolSize(poolSize);
        return dataSource;
    }

    /**
     * This method creates a component that will be used as a session factory bean.
     *
     * @return the LocalSessionFactoryBean
     */
    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("com.epam.esm");
        return sessionFactory;
    }

    /**
     * This method creates a component that will be used as a platform transaction manager.
     *
     * @return the PlatformTransactionManager
     */
    @Bean
    public PlatformTransactionManager hibernateTransactionManager(LocalSessionFactoryBean sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory.getObject());
        return transactionManager;
    }
}
