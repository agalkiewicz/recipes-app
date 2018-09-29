package com.example.recipesapp.dto;

import java.util.HashMap;

public class KeyValueDTO {
    HashMap<String, String> propertyToChange;

    public HashMap<String, String> getPropertyToChange() {
        return propertyToChange;
    }

    public void setPropertyToChange(HashMap<String, String> propertyToChange) {
        this.propertyToChange = propertyToChange;
    }
}
