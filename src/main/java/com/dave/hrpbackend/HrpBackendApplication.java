package com.dave.hrpbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dave.hrpbackend.mapper")
public class HrpBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrpBackendApplication.class, args);
    }

}
