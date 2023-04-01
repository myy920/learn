package com.myy.spring.a11;

import java.lang.reflect.Proxy;

public class JdkProxyDemo {

    public static void main(String[] args) {
        Target target = new Target();
        Foo targetProxy = (Foo) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                (proxy, method, args1) -> {
                    System.out.println("before");
                    Object res = method.invoke(target, args1);
                    System.out.println("after");
                    return res;
                });
        System.out.println(targetProxy.getClass()); // $Proxy0
        targetProxy.foo();
    }

    interface Foo {
        void foo();
    }

    static class Target implements Foo {
        @Override
        public void foo() {
            System.out.println("foo");
        }
    }

}
