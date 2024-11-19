package com.donggu.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DongguApplication {

    public static void main(String[] args) {
        SpringApplication.run(DongguApplication.class, args);
    }

}
