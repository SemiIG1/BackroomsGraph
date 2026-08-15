package com.fauzan.backrooms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackroomsGraphApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackroomsGraphApplication.class, args);
    }
}
