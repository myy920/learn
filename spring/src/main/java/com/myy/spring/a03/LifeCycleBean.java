package com.myy.spring.a03;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Component
public class LifeCycleBean implements BeanFactoryAware, BeanNameAware, ApplicationContextAware , InitializingBean {

    public LifeCycleBean() {
        System.out.println("构造!");
    }

    //@Autowired
    @Resource
    private void autowired(@Value("${JAVA_HOME}") String home) {
        System.out.println("@Autowired注解标记的方法! 依赖注入 home=" + home);
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("@PostConstruct注解标记的方法! 初始化");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("@PreDestroy注解标记的方法! 销毁");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryAware");
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("BeanNameAware");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("ApplicationContextAware");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean");
    }
}
