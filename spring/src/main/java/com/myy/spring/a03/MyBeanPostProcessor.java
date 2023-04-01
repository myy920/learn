package com.myy.spring.a03;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements InstantiationAwareBeanPostProcessor, DestructionAwareBeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (isLifeCycleBean(beanName)) {
            System.out.println("初始化之前执行, 返回的对象会替换掉原本的Bean, 如@PostConstruct,@ConfigurationProperties");
        }
        return InstantiationAwareBeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (isLifeCycleBean(beanName)) {
            System.out.println("初始化之后执行, 返回的对象会替换原本的Bean, 如代理增强");
        }
        return InstantiationAwareBeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (isLifeCycleBean(beanName)) {
            System.out.println("实例化之前执行, 返回的对象替换掉原本的Bean");
        }
        return InstantiationAwareBeanPostProcessor.super.postProcessBeforeInstantiation(beanClass, beanName);
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (isLifeCycleBean(beanName)) {
            System.out.println("实例化之后执行, 返回false会跳过依赖注入阶段");
        }
        return InstantiationAwareBeanPostProcessor.super.postProcessAfterInstantiation(bean, beanName);
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (isLifeCycleBean(beanName)) {
            System.out.println("依赖注入阶段执行, 如@Autowired,@Value,@Resource");
        }
        return InstantiationAwareBeanPostProcessor.super.postProcessProperties(pvs, bean, beanName);
    }

    @Override
    public void postProcessBeforeDestruction(Object o, String beanName) throws BeansException {
        if (isLifeCycleBean(beanName)) {
            System.out.println("销毁前执行, 如@PreDestory");
        }
    }


    private boolean isLifeCycleBean(String beanName) {
        return "lifeCycleBean".equals(beanName);
    }

}
