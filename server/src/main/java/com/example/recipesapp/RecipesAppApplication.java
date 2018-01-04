package com.example.recipesapp;

import com.example.recipesapp.htmlanalysis.HtmlAnalysisService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import com.example.recipesapp.recipe.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class RecipesAppApplication implements CommandLineRunner {

    private final RecipeRepository recipeRepository;

    private final RecipeDAO recipeDAO;

    private final HtmlAnalysisService htmlAnalysisService;

    public RecipesAppApplication(RecipeRepository recipeRepository,
                                 RecipeDAO recipeDAO,
                                 HtmlAnalysisService htmlAnalysisService) {
        this.recipeRepository = recipeRepository;
        this.recipeDAO = recipeDAO;
        this.htmlAnalysisService = htmlAnalysisService;
    }

    public static void main(String[] args) {
        SpringApplication.run(RecipesAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        htmlAnalysisService.analyse("https://www.przyslijprzepis.pl/przepis/leczo-z-dynia");
        recipeDAO.getRecipeByIngredients("cebula");
    }
}
