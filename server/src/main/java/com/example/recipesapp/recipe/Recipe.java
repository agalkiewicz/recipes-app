package com.example.recipesapp.recipe;

import javax.persistence.*;

@Entity(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_generator")
    @SequenceGenerator(name = "recipe_generator", sequenceName = "recipe_sequence", allocationSize = 1)
    private Long id;

    private String title;

    private String ingredients;

    public Recipe() {
    }

    public Recipe(String title) {
        this.title = title;
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

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        String result = String.format("Recipe[id=%d, title='%s']%n", id, title);
        result += String.format("Ingredient[ingredient='%s']%n", ingredients);

        return result;
    }
}
