package com.myy.spring.a27;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

public class MyController {

    public ModelAndView test1() {
        System.out.println("test1");
        ModelAndView mav = new ModelAndView("view1");
        mav.addObject("name", "张三");
        return mav;
    }

    public String test2() {
        System.out.println("test2");
        return "view2";
    }

    @ModelAttribute
    public User test3() {
        System.out.println("test3");
        return new User("test3", 22);
    }

    public User test4() {
        System.out.println("test4");
        return new User("test4", 33);
    }

    public HttpEntity<User> test5() {
        System.out.println("test5");
        return new HttpEntity<>(new User("test5", 11));
    }

    public HttpHeaders test6() {
        System.out.println("test6");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "text/html");
        return httpHeaders;
    }

    @ResponseBody
    public User test7() {
        System.out.println("test7");
        return new User("test7", 36);
    }

}
