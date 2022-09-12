package com.example.itmonster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class ITmonsterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ITmonsterApplication.class, args);
    }
}
