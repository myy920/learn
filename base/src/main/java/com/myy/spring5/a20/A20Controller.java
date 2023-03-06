package com.myy.spring5.a20;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class A20Controller {

    private static final Logger log = LoggerFactory.getLogger(A20Controller.class);

    @GetMapping("/a")
    public ModelAndView a() {
        log.debug("a exec");
        return null;
    }

    @PostMapping("/b")
    public ModelAndView b(@RequestParam String name) {
        log.debug("b exec {}", name);
        return null;
    }

    @PostMapping("/c")
    public ModelAndView c(@Token String token) {
        log.debug("c exec {}", token);
        return null;
    }

    @RequestMapping("/d.yml")
    @Yml
    public Person d() {
        log.debug("d exec");
        return new Person("关羽", 18);
    }
}
