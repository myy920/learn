package com.myy.spring.a09;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class A09Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(A09Application.class);
        MyService myService = context.getBean(MyService.class);
        myService.foo();
        MyService.staticFoo();
        context.close();

    }

}
