package com.myy.spring.a13;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

public class Demo {

    public static void main(String[] args) throws Throwable {
        Target target = new Target();

        Target targetProxy = (Target) Enhancer.create(target.getClass(),
                (MethodInterceptor) (proxy, method, arguments, methodProxy) -> {
                    System.out.println("before");
                    Object res = null;
                    // 1.通过方法反射调用
                    res = method.invoke(target, arguments);
                    // 2.通过FastClass直接调用目标对象的方法
                    res = methodProxy.invoke(target, arguments);
                    // 3.通过FastClass调用目标代理类中生成的方法
                    res = methodProxy.invokeSuper(proxy, arguments);
                    System.out.println("after");
                    return res;
                });

        System.out.println(Target.class);
        System.out.println(targetProxy.getClass());
        targetProxy.foo();
        System.out.println("--------");
        targetProxy.foo(66);
        System.out.println("--------");
        targetProxy.foo("nice!");
        System.in.read();
    }

}
