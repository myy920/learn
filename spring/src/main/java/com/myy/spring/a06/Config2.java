package com.myy.spring.a06;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config2 implements ApplicationContextAware, InitializingBean {

    @Autowired
    public void autowired(ApplicationContext applicationContext) {
        System.out.println("@Autowired注入applicationContext=" + applicationContext);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("applicationContext=" + applicationContext);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean回调");
    }

    @Bean
    public BeanFactoryPostProcessor beanFactoryPostProcessor() {
        return configurableListableBeanFactory -> System.out.println("自定义BeanFactoryPostProcessor");
    }

}



