package com.myy.spring.a08.scope1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ScopeController {

    @Lazy
    @Autowired
    private BeanForSingleton beanForSingleton;
    @Lazy
    @Autowired
    private BeanForPrototype beanForPrototype;
    @Lazy
    @Autowired
    private BeanForRequest beanForRequest;
    @Lazy
    @Autowired
    private BeanForSession beanForSession;
    @Lazy
    @Autowired
    private BeanForApplication beanForApplication;

    @GetMapping("/scope")
    public String scope(HttpServletRequest request) {
        String info = "<ul>" +
                "<li>" + "Singleton=" + beanForSingleton + "\n" + "</li>" +
                "<li>" + "Prototype=" + beanForPrototype + "\n" + "</li>" +
                "<li>" + "Request=" + beanForRequest + "\n" + "</li>" +
                "<li>" + "Session=" + beanForSession + "\n" + "</li>" +
                "<li>" + "Application=" + beanForApplication + "\n" + "</li>" + "</ul>";
        return info;
    }

}
