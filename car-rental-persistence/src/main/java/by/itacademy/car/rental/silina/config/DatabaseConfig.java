package by.itacademy.car.rental.silina.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:db.properties")
@EnableJpaRepositories(basePackages = "by.itacademy.car.rental.silina.dao")
@EnableTransactionManagement
public class DatabaseConfig {
    @Value("${jdbc.driverClassName}")
    private String driverName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.user}")
    private String user;

    @Value("${jdbc.pass}")
    private String pass;

    @Bean
    protected HikariConfig hikariConfig() {
        var hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverName);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(user);
        hikariConfig.setPassword(pass);
        return hikariConfig;
    }

    @Primary
    @Bean
    protected DataSource getDataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    protected HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    protected LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        var emfBean = new LocalContainerEntityManagerFactoryBean();
        emfBean.setDataSource(getDataSource());
        emfBean.setJpaVendorAdapter(hibernateJpaVendorAdapter());
        emfBean.setPackagesToScan("by.itacademy.car.rental.silina");

        var jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        jpaProperties.put("hibernate.connection.autocommit", "false");
        jpaProperties.put("hibernate.show_sql", "true");
        jpaProperties.put("hibernate.format_sql", "true");

        emfBean.setJpaProperties(jpaProperties);

        return emfBean;
    }

    @Bean
    protected PlatformTransactionManager transactionManager() {
        var jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return jpaTransactionManager;
    }

    @Bean
    protected TransactionTemplate transactionTemplate() {
        var transactionTemplate = new TransactionTemplate();
        transactionTemplate.setTransactionManager(transactionManager());
        transactionTemplate.setTimeout(10000);
        return transactionTemplate;
    }
}
