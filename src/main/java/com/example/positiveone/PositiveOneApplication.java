package com.example.positiveone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PositiveOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(PositiveOneApplication.class, args);
    }

}
