package com.myy.springdemo.factory;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class FactoryBeanRegistrySupport extends SingletonBeanRegistry {

    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap(16);

    protected Object getCachedObjectForFactoryBean(String beanName) {
        return this.factoryBeanObjectCache.get(beanName);
    }

    protected synchronized Object getObjectFromFactoryBean(FactoryBean<?> factory, String beanName) {
        Object object = factoryBeanObjectCache.get(beanName);
        if (object == null) {
            object = doGetObjectFromFactoryBean(factory, beanName);
        }
        object = this.postProcessObjectFromFactoryBean(object, beanName);
        factoryBeanObjectCache.put(beanName, object);
        return object;
    }

    private Object doGetObjectFromFactoryBean(FactoryBean<?> factoryBean, String beanName) {
        try {
            return factoryBean.getObject();
        } catch (Exception e) {
            throw new BeanCreationException("FactoryBean threw exception on object creation");
        }
    }

    protected Object postProcessObjectFromFactoryBean(Object object, String beanName) {
        return object;
    }

}
