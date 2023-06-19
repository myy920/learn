package com.myy.springdemo;

import com.myy.springdemo.util.Target;
import org.springframework.context.support.GenericApplicationContext;

public class Demo {

    public static void main(String[] args) {

        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean(Target.class);

        context.refresh();


    }

}
