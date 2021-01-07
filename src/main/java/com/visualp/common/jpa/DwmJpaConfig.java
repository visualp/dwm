package com.visualp.common.jpa;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.common.jpa
 * 3. 작성일     : 2021. 01. 04. 오전 11:47
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.visualp." + DwmJpaConfig.dbname},
        transactionManagerRef = DwmJpaConfig.TransactionManager,
        entityManagerFactoryRef = DwmJpaConfig.EntityManager
)
public class DwmJpaConfig {
    public static final String dbname = "dwm";

    @Value("${spring.profiles.active}")
    public String active;

    @Value("${" + dbname + ".url}")
    public String url;

    @Value("${" + dbname + ".user}")
    public String user;

    @Value("${" + dbname + ".password}")
    public String password;

    @Value("${" + dbname + ".poolname}")

    public String poolname;

    @Value("${" + dbname + ".poolsize}")
    public Integer poolsize;

    public final static String EntityManager = dbname + "_entityManagerFactory";
    public final static String TransactionManager = dbname + "_transactionManager";

    @Primary
    @Bean(name = EntityManager)
    public LocalContainerEntityManagerFactoryBean dwm_entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dwm_DataSource());
        em.setPackagesToScan("com.visualp." + dbname);
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(JpaProperties.mariadb_additionalProperties(active));
        return em;
    }

    @Primary
    @Bean
    public DataSource dwm_DataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        Properties properties = new Properties();
        hikariConfig.setDataSourceClassName(org.mariadb.jdbc.MariaDbDataSource.class.getName());
        properties.setProperty("url", url);
        properties.setProperty("user", user);
        properties.setProperty("password", password);
        hikariConfig.setPoolName(poolname);
        hikariConfig.setMaximumPoolSize(poolsize);
        //  hikariConfig.setLeakDetectionThreshold(0);
        //  hikariConfig.setIdleTimeout(600000);
        //  hikariConfig.setMaxLifetime(1800000);
        //  hikariConfig.setConnectionTimeout(300000);
        hikariConfig.setDataSourceProperties(properties);
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        if (active.equals("dev")) {
            Log4JdbcCustomFormatter log4JdbcCustomFormatter = new Log4JdbcCustomFormatter();
            log4JdbcCustomFormatter.setLoggingType(LoggingType.MULTI_LINE);
            log4JdbcCustomFormatter.setSqlPrefix("SQL:::");
            Log4jdbcProxyDataSource log4jdbcProxyDataSource = new Log4jdbcProxyDataSource(dataSource);
            log4jdbcProxyDataSource.setLogFormatter(log4JdbcCustomFormatter);
            return log4jdbcProxyDataSource;
        } else {
            return new LazyConnectionDataSourceProxy(dataSource);
        }
    }

    @Bean(name = TransactionManager)
    public PlatformTransactionManager dwm_transactionManager(@Qualifier(EntityManager) EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
    
}
