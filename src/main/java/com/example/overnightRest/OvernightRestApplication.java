package com.example.overnightRest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.overnightRest", "com.example.common.*"})
@EntityScan(basePackages = "com.example.common.*")
@EnableJpaRepositories(basePackages = "com.example.common.*")
public class OvernightRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(OvernightRestApplication.class, args);
    }

}
