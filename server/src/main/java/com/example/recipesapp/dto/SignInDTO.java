package com.example.recipesapp.dto;

public class SignInDTO {
    private String idToken;

    public SignInDTO() {
    }

    public SignInDTO(String idToken) {
        this.idToken = idToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    @Override
    public String toString() {
        return "SignInDTO{" +
                "idToken='" + idToken + '\'' +
                '}';
    }
}
