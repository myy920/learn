package com.myy.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/v1/test")
    public void test(){
        System.out.println(1);
        System.out.println(2);
    }

}
