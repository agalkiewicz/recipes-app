package com.example.recipesapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.recipesapp.recipe.*;

import java.util.stream.Stream;

@SpringBootApplication
public class RecipesAppApplication implements CommandLineRunner {

    private final RecipeRepository recipeRepository;

    public RecipesAppApplication(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(RecipesAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Stream.of("Placki ziemniaczane", "Zupa rybna", "Tort bezowy")
                .forEach(name -> recipeRepository.save(new Recipe(name)));
        recipeRepository.findAll().forEach(System.out::println);

    }
}
