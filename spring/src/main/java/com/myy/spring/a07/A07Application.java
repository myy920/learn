package com.myy.spring.a07;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class A07Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(A07Application.class);
        context.close();
    }

    @Bean(initMethod = "initMethod",destroyMethod = "destroyMethod")
    public Bean1 bean1(){
        return new Bean1();
    }

}
