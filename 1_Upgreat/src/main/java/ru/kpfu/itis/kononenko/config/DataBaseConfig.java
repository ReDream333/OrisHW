package ru.kpfu.itis.kononenko.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;


import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "ru.kpfu.itis.kononenko.repository")
@PropertySource("classpath:application.properties")
@ComponentScan("ru.kpfu.itis.kononenko")
@RequiredArgsConstructor

public class DataBaseConfig {

    private final Environment env;

    @Bean
    public DataSource getDataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(env.getProperty("db.driver.classname"));
        hikariConfig.setJdbcUrl(env.getProperty("db.url"));
        hikariConfig.setUsername(env.getProperty("db.username"));
        hikariConfig.setPassword(env.getProperty("db.password"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(Objects.requireNonNull(env.getProperty("db.hikari.max-pool-size"))));
        return hikariConfig;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.ddl.auto", "validate");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        properties.setProperty("hibernate.generate_statistics", "true");
        properties.setProperty("hibernate.format_sql", "true");
        return properties;


    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(getDataSource());
        factoryBean.setPackagesToScan("ru.kpfu.itis.kononenko.model");
        factoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        factoryBean.setJpaProperties(hibernateProperties());

        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

}

