package com.project.cardata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@MapperScan(value ={"com.project.cardata.*"})

@SpringBootApplication

public class CarDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarDataApplication.class, args);
    }

}
