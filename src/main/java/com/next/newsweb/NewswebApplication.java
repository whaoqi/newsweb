package com.next.newsweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.next.newsweb.mapper")//没写Mapper注解的话这样扫描
public class NewswebApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewswebApplication.class, args);
    }

}
