package com.example.recipesapp.dto;

public class RecipeUrlDTO {

    private String url;

    public RecipeUrlDTO() {
    }

    public RecipeUrlDTO(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "RecipeUrlDTO{" +
                "url='" + url + '\'' +
                '}';
    }
}
