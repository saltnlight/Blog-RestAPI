package com.flora.week9taskblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Week9TaskBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(Week9TaskBlogApplication.class, args);
    }

}
