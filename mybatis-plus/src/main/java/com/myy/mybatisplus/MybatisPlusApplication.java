package com.myy.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com/myy/mybatisplus/mapper")
public class MybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusApplication.class, args);

       /* FastAutoGenerator.create("jdbc:mysql://192.168.111.128:3306/dev", "root", "Root*123")
                .globalConfig(builder -> {
                    builder.author("myy")
                            .outputDir("F:\\code\\learn\\mybatis-plus\\src\\main\\java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.myy.mybatisplus");
                })
                .strategyConfig(builder -> {
                    builder.addInclude("user");
                    builder.entityBuilder().enableLombok();
                    builder.serviceBuilder().convertServiceFileName(entityName -> entityName + "Service");
                    builder.controllerBuilder().enableRestStyle();
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();*/


    }


}
