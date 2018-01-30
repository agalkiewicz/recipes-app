package com.example.recipesapp.exceptions;

public class ScopeNotFoundException extends Exception {

    private String message = "Cannot analyse recipe from this website.";

    @Override
    public String getMessage() {
        return message;
    }
}
