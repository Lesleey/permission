package com.imooc.permission;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lesleey
 * @date 2021/5/3-16:16
 * @function
 */
@SpringBootApplication
@MapperScan(basePackages = "com.imooc.permission.dao")
@RequestMapping("hello")
@RestController
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @RequestMapping
    public String heelo(){
        ClassPathResource classPathResource = new ClassPathResource("WEB-INFO/demo.jsp");
        System.out.println(classPathResource.exists());
        return "index";
    }

}
