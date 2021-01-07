package com.visualp.common.jpa;
import java.util.Properties;

/**
 * 1. 프로젝트명 : api
 * 2. 패키지명   : com.visualp.common.jpa
 * 3. 작성일     : 2020. 06. 25. 오후 3:22
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
public class JpaProperties {

    public static Properties mariadb_additionalProperties(String active) {

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "validate");
        properties.setProperty("hibernate.dialect", org.hibernate.dialect.MariaDB103Dialect.class.getName());
        //물리(영문, 테이블명
        properties.setProperty("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        //논리(설명)
        properties.setProperty("hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
        properties.setProperty("hibernate.use-new-id-generator-mappings", "true");
        properties.setProperty("hibernate.jdbc.batch_size", "100");
        properties.setProperty("hibernate.order_inserts","true");
        properties.setProperty("hibernate.id.new_generator_mappings","true");

        if (active.equals("dev")) {
            properties.setProperty("hibernate.show_sql", "true");
            properties.setProperty("hibernate.format_sql", "true");
            properties.setProperty("hibernate.use_sql_comments", "true");
        }
        return properties;
    }

}
