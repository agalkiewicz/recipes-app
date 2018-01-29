package com.example.recipesapp.dto;

import com.example.recipesapp.recipe.Recipe;

import java.util.Arrays;

public class RecipeDTO {
    private Long id;
    private String title;
    private String[] ingredients;

    public RecipeDTO() {
    }

    public RecipeDTO(Recipe recipe) {
        this.id = recipe.getId();
        this.title= recipe.getTitle();
        this.ingredients = recipe.getIngredients().split("@");
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    @Override
    public String toString() {
        return "RecipeDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                '}';
    }
}
