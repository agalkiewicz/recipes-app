package com.example.recipesapp.auth;

public class InvalidTokenException extends Exception {
    public InvalidTokenException(String message) {
        super(message);
    }
}
