package com.myy.web.controller;

import com.myy.service.DepartmentService;
import com.myy.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/department")
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    @GetMapping
    public Result<?> query(){
        return Result.ok(departmentService.queryAllDepartment());
    }

}
