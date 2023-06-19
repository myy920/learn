package com.myy.springdemo.factory;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例注册
 */
public abstract class SingletonBeanRegistry {

    // 一级缓存: 完全的Bean实例
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap(256);
    // 二级缓存: 不完整的Bean实例, 只是实例还未完成依赖注入
    private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap(16);
    // 三级缓存: 创建Bean实例的对象工厂
    private final Map<String, ObjectFactory> singletonFactories = new HashMap(16);
    // Bean的依赖
    private final Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap(64);
    // 依赖的Bean
    private final Map<String, Set<String>> dependenciesForBeanMap = new ConcurrentHashMap(64);

    public synchronized void registryBean(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
    }

    public synchronized Object getSingleton(String beanName, ObjectFactory objectFactory) {
        Object singletonObject = singletonObjects.get(beanName);
        if (singletonObject == null) {
            singletonObject = objectFactory.getObject();
            registryBean(beanName, singletonObject);
        }
        return singletonObject;
    }

    public synchronized Object getSingleton(String beanName) {
        // 先从一级缓存中取
        Object singletonObject = singletonObjects.get(beanName);
        if (singletonObject == null) {
            // 一级缓存中没取到再从二级缓存中找
            singletonObject = earlySingletonObjects.get(beanName);
            if (singletonObject == null) {
                // 二级缓存中没找到再从三级缓存中找
                ObjectFactory singletonFactory = singletonFactories.get(beanName);
                if (singletonFactory != null) {
                    // 三级缓存不为空时, 则通过对象工厂获取对象, 确保只存入二级缓存中
                    singletonObject = singletonFactory.getObject();
                    earlySingletonObjects.put(beanName, singletonObject);
                    singletonObjects.remove(beanName);
                }
            }
        }
        return singletonObject;
    }

    public synchronized void registryDependentBean(String beanName, String dependentBeanName) {
        Set<String> set;
        set = dependentBeanMap.computeIfAbsent(beanName, key -> new LinkedHashSet<>(8));
        set.add(dependentBeanName);
        set = dependenciesForBeanMap.computeIfAbsent(dependentBeanName, key -> new LinkedHashSet<>(8));
        set.add(beanName);
    }

    public synchronized String[] getDependentBeans(String beanName) {
        Set<String> set = dependentBeanMap.get(beanName);
        return set == null ? new String[0] : set.toArray(new String[0]);
    }

    public synchronized String[] getDependenciesForBeans(String beanName) {
        Set<String> set = dependenciesForBeanMap.get(beanName);
        return set == null ? new String[0] : set.toArray(new String[0]);
    }

}
