package com.myy.spring.a09;

import org.springframework.stereotype.Service;

@Service
public class MyService {

    public void foo() {
        System.out.println("foo");
    }

    public static void staticFoo() {
        System.out.println("static foo");
    }

}
