package com.myy.spring5.a20;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.yaml.snakeyaml.Yaml;

@Data
@AllArgsConstructor
public class Person {

    private String name;

    private Integer age;

    // 测试一下yml转换
    public static void main(String[] args) {
        String str = new Yaml().dump(new Person("关羽", 18));
        System.out.println(str);
    }

}
