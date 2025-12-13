package com.cinema_package.cinema_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;



@SpringBootApplication
@RestController
@RequestMapping("/movie")
public class CinemaProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaProjectApplication.class, args);
    }

    public static class NewMovieRequest {

        public NewMovieRequest() {
        }
    }
}
