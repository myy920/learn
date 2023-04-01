package com.myy.spring.a15;

public class Target implements FooBar {

    public void foo() {
        System.out.println("foo");
    }

    @Anno
    public void bar() {
        System.out.println("bar");
    }

}
