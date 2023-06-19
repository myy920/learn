package com.myy.springdemo.factory;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport {

    private final Map<String, RootBeanDefinition> mergedBeanDefinitions = new ConcurrentHashMap<>(256);

    private final Set<String> alreadyCreated = Collections.newSetFromMap(new ConcurrentHashMap<>(256));

    protected <T> T getBean(String beanName) {
        return doGetBean(beanName, null, null, false);
    }

    protected <T> T doGetBean(String beanName, Class<T> requiredType, Object[] args, boolean typeCheckOnly) {
        Object sharedInstance = getSingleton(beanName);
        if (sharedInstance != null) {
            // getObjectFromBeanInstance
        } else {
            // 从父Bean工厂中获取到就返回
            if (!typeCheckOnly) {
                markBeanAsCreated(beanName);
            }
            RootBeanDefinition rbd = getRootBeanDefinition(beanName);
            String[] dependsOn = rbd.getDependsOn();
            if (dependsOn != null) {
                for (String dep : dependsOn) {
                    registryDependentBean(dep, beanName);
                    getBean(dep);

                }
            }
            if (rbd.isSingleton()) {
                getSingleton(beanName, () -> createBean(beanName,rbd,args));
            }
        }
        return null;
    }

    protected abstract Object createBean(String beanName, RootBeanDefinition rbd, Object[] args);

    protected RootBeanDefinition getRootBeanDefinition(String beanName) {
        RootBeanDefinition rbd = mergedBeanDefinitions.get(beanName);
        return rbd == null ? new RootBeanDefinition(getBeanDefinition(beanName)) : rbd;
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName);

    protected synchronized void markBeanAsCreated(String beanName) {
        if (!this.alreadyCreated.contains(beanName)) {
            alreadyCreated.add(beanName);
        }
    }

}
