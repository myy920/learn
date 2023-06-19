package com.myy.springdemo.factory;

public interface BeanDefinition {

    String SINGLETON = "singleton";

    String PROTOTYPE = "prototype";

    String getScope();

    void setScope(String scope);

    String[] getDependsOn();

    void setDependsOn(String... dependsOn);

    boolean isSingleton();

    boolean isPrototype();

}
