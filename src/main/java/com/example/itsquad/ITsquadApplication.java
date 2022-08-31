package com.example.itsquad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ITsquadApplication {

    public static void main(String[] args) {
        SpringApplication.run(ITsquadApplication.class, args);
    }
}