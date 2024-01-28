package com.myy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/nonRepeat")
public class NonRepeatController {

    @Resource
    private NonRepeatService nonRepeatService;

    @GetMapping("/getToken")
    String getToken() {
        return nonRepeatService.createToken();
    }

}
