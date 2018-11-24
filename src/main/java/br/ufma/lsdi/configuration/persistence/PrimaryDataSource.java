package br.ufma.lsdi.configuration.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager", basePackages = {
        "br.ufma.lsdi.model"})
@EnableTransactionManagement
public class PrimaryDataSource {

    @Autowired
    private Environment env;

    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean userEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("br.ufma.lsdi.model");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql",env.getProperty("spring.jpa.show-sql"));
        properties.put("hibernate.enable_lazy_load_no_trans", "true");

        em.setJpaPropertyMap(properties);
        return em;
    }
    @Bean
    @Primary
    public DataSource dataSource() {
        HikariConfig dataSource = new HikariConfig();
        dataSource.setDriverClassName(env.getProperty("datasource.driver-class-name"));
        dataSource.setJdbcUrl(env.getProperty("datasource.primary.url"));
        dataSource.setUsername(env.getProperty("datasource.primary.username"));
        dataSource.setPassword(env.getProperty("datasource.primary.password"));
        dataSource.addDataSourceProperty( "cachePrepStmts" , "true" );
        dataSource.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        dataSource.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        dataSource.setMaximumPoolSize(5);
        dataSource.setConnectionTimeout(20000);
        dataSource.setMaxLifetime(1200000);
        dataSource.setMinimumIdle(1);
        dataSource.setIdleTimeout(100000);
        HikariDataSource ds = new HikariDataSource( dataSource );
        return ds;
    }
    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager userTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(userEntityManager().getObject());
        return transactionManager;
    }
}
