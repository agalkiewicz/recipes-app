package com.example.recipesapp.dto;

import com.example.recipesapp.recipe.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeDTO {
    private Long id;
    private String title;
    private List<String> ingredients;
    private String url;
    private String image;
    private String description;
    private List<String> instructions;
    private List<String> categories;

    public RecipeDTO() {
        initializeLists();
    }

    public RecipeDTO(String url) {
        this.url = url;
        initializeLists();
    }

    public RecipeDTO(Recipe recipe) {
        initializeLists();

        this.id = recipe.getId();
        this.title = recipe.getTitle();
        if (recipe.getIngredients() != null) {
            this.ingredients = Arrays.asList(recipe.getIngredients().split("@"));
        }
        this.url = recipe.getUrl();
        this.image = recipe.getImage();
        this.description = recipe.getDescription();
        if (recipe.getInstructions() != null) {
            this.instructions = Arrays.asList(recipe.getInstructions().split("@"));
        }
        if (recipe.getCategories() != null) {
            this.categories = Arrays.asList(recipe.getCategories().split("@"));
        }
    }

    public void addIngredient(String ingredient) {
        this.ingredients.add(ingredient);
    }

    public void addInstruction(String instruction) {
        this.instructions.add(instruction);
    }

    public void addCategory(String category) {
        this.categories.add(category);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String ingredients = this.ingredients
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        String instructions = this.instructions
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        String categories = this.categories
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        stringBuilder
                .append("RecipeDTO{ id=").append(id)
                .append(", title=").append(title).append("\n")
                .append(", url=").append(url).append("\n")
                .append(", image=").append(image).append("\n")
                .append(", description=").append(description).append("\n")
                .append(", ingredients=").append(ingredients).append("\n")
                .append(", instructions=").append(instructions).append("\n")
                .append(", categories=").append(categories).append("}");

        return stringBuilder.toString();
    }

    private void initializeLists() {
        this.ingredients = new ArrayList<>();
        this.instructions = new ArrayList<>();
        this.categories = new ArrayList<>();
    }
}
