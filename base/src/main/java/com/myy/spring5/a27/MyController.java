package com.myy.spring5.a27;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

public class MyController {

    public ModelAndView test1() {
        System.out.println("test1");
        ModelAndView mav1 = new ModelAndView("mav1");
        mav1.addObject("name", "关羽");
        return mav1;
    }

    public String test2() {
        System.out.println("test2");
        return "test2";
    }

    @ModelAttribute
    public Person test3() {
        System.out.println("test3");
        return new Person("张飞", 16);
    }

    public Person test4() {
        System.out.println("test4");
        return new Person("刘备", 20);
    }

    public HttpEntity<Person> test5() {
        System.out.println("test5");
        return new HttpEntity<>(new Person("赵云", 17));
    }

    public HttpHeaders test6() {
        System.out.println("test6");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "text/html");
        return httpHeaders;
    }

    @ResponseBody
    public Person test7() {
        System.out.println("test7");
        return new Person("马超", 15);
    }


}

