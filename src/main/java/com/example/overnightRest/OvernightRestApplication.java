package com.example.overnightRest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.overnightRest", "com.example.common.*"})
@EntityScan(basePackages = "com.example.common.*")
@EnableJpaRepositories(basePackages = "com.example.common.*")
@EnableAsync
@EnableScheduling
public class OvernightRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(OvernightRestApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
