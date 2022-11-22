package com.brd.candi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class CandiApplication {
    public static void main(String[] args) {
        SpringApplication.run(CandiApplication.class, args);
    }
}
