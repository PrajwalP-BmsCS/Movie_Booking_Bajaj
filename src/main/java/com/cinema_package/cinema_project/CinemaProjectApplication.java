package com.cinema_package.cinema_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;



@SpringBootApplication
@EnableKafka
@EnableCaching

public class CinemaProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaProjectApplication.class, args);
    }

}
