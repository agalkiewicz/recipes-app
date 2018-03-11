package com.example.recipesapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableWebMvc
public class RecipesAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipesAppApplication.class, args);
    }
}
