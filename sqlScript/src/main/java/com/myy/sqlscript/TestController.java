package com.myy.sqlscript;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/test")
public class TestController {

    @Resource
    private SqlScriptService sqlScriptService;

    @GetMapping("/a")
    void a(@RequestParam String table) {
        ScriptParam param = new ScriptParam("casea/createCaseA.sql");
        param.addParam("$table", table);
        sqlScriptService.exec(param);
    }

}
