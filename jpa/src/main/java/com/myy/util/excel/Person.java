package com.myy.util.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ExcelStyle(StandardTableStyle.class)
public class Person {

    private String id;

    @ExcelProperty(value = "姓名")
    private String name;

    private Integer age;

}
