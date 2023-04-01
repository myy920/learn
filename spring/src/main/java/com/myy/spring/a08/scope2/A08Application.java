package com.myy.spring.a08.scope2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.myy.spring.a09")
public class A08Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(A08Application.class);

        Bean1 bean1 = context.getBean(Bean1.class);
        System.out.println(bean1);

        System.out.println(bean1.bean2);
        System.out.println(bean1.bean2);
        System.out.println(bean1.bean2);

        System.out.println(bean1.bean3);
        System.out.println(bean1.bean3);
        System.out.println(bean1.bean3);

        System.out.println(bean1.getBean4());
        System.out.println(bean1.getBean4());
        System.out.println(bean1.getBean4());

        System.out.println(bean1.getBean5());
        System.out.println(bean1.getBean5());
        System.out.println(bean1.getBean5());

        context.close();
    }

}
