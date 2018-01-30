package com.example.recipesapp.dto;

import com.example.recipesapp.recipe.Recipe;

import java.util.Arrays;

public class RecipeDTO {
    private Long id;
    private String title;
    private String[] ingredients;
    private String url;
    private String image;
    private String description;
    private String[] instructions;
    private String[] categories;

    public RecipeDTO() {
    }

    public RecipeDTO(String url) {
        this.url = url;
    }

    public RecipeDTO(Recipe recipe) {
        this.id = recipe.getId();
        this.title = recipe.getTitle();
        if (recipe.getIngredients() != null) {
            this.ingredients = recipe.getIngredients().split("@");
        }
        this.url = recipe.getUrl();
        this.image = recipe.getImage();
        this.description = recipe.getDescription();
        if (recipe.getInstructions() != null) {
            this.instructions = recipe.getInstructions().split("@");
        }
        if (recipe.getCategories() != null) {
            this.categories = recipe.getCategories().split("@");
        }
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

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String[] getInstructions() {
        return instructions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInstructions(String[] instructions) {
        this.instructions = instructions;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("RecipeDTO{ id=").append(id)
                .append(", title=").append(title).append("\n")
                .append(", url=").append(url).append("\n")
                .append(", image=").append(image).append("\n")
                .append(", description=").append(description).append("\n")
                .append(", ingredients=").append(Arrays.toString(ingredients)).append("\n")
                .append(", instructions=").append(Arrays.toString(instructions)).append("\n")
                .append(", categories=").append(Arrays.toString(categories)).append("}");

        return stringBuilder.toString();
    }
}
