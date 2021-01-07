package com.visualp.common.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 1. 프로젝트명 : api
 * 2. 패키지명   : com.visualp.common.jpa
 * 3. 작성일     : 2020. 06. 18. 오후 6:43
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
@Configuration
@EnableTransactionManagement
public class CtxConfig {
    public static final String TransactionManager = "chained_transactionManager";

    @Bean(name = TransactionManager)
    @Primary
    @Autowired
    public ChainedTransactionManager transactionManager(
            @Qualifier(DwmJpaConfig.TransactionManager) PlatformTransactionManager txManager1
    ) {
        return new ChainedTransactionManager(txManager1);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

}
