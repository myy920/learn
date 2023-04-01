package com.myy.spring.a05;

import com.myy.spring.a05.processor.MapperPostProcessor;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

public class A05Application {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("config", Config.class);

        context.registerBean(ConfigurationClassPostProcessor.class);
        context.registerBean(MapperPostProcessor.class);

        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
    }
}
