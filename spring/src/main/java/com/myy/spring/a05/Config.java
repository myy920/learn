package com.myy.spring.a05;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.myy.spring.a05.component")
public class Config {

    @Bean
    public Bean1 bean1() {
        return new Bean1();
    }

//    @Bean
//    public HikariDataSource hikariDataSource() {
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setJdbcUrl("jdcb:mysql://localhost:3306");
//        dataSource.setUsername("root");
//        dataSource.setPassword("Root*123");
//        return dataSource;
//    }

//    @Bean
//    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
//        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
//        sessionFactoryBean.setDataSource(dataSource);
//        return sessionFactoryBean;
//    }
}
