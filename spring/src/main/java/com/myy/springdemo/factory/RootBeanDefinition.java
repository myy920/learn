package com.myy.springdemo.factory;

public class RootBeanDefinition implements BeanDefinition {

    private String scope;

    private String[] dependsOn;

    public RootBeanDefinition() {
    }

    public RootBeanDefinition(BeanDefinition beanDefinition) {


    }

    @Override
    public String getScope() {
        return scope;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String[] getDependsOn() {
        return dependsOn;
    }

    @Override
    public void setDependsOn(String[] dependsOn) {
        this.dependsOn = dependsOn;
    }

    @Override
    public boolean isSingleton() {
        return SINGLETON.equals(scope);
    }

    @Override
    public boolean isPrototype() {
        return PROTOTYPE.equals(scope);
    }
}
