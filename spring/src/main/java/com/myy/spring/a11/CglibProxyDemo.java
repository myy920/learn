package com.myy.spring.a11;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

public class CglibProxyDemo {

    public static void main(String[] args) {
        Target target = new Target();
        Target proxy = (Target) Enhancer.create(target.getClass(),
                (MethodInterceptor) (p, method, args1, methodProxy) -> {
                    System.out.println("before");
                    Object res = method.invoke(target, args1);
                    System.out.println("after");
                    return res;
                });
        System.out.println(proxy.getClass()); // Target$$EnhancerByCGLIB
        proxy.foo();
    }

    static class Target {

        public void foo() {
            System.out.println("foo");
        }

    }

}
