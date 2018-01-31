package com.example.recipesapp.htmlanalysis;

import com.example.recipesapp.dto.RecipeDTO;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class RecipeDTOAdapter extends TypeAdapter {

    @Override
    public void write(JsonWriter jsonWriter, Object o) throws IOException {

    }

    @Override
    public RecipeDTO read(final JsonReader jsonReader) throws IOException {
        RecipeDTO recipeDTO = null;
        boolean stop = false;

        jsonReader.beginObject();
        while (!stop && jsonReader.hasNext()) {
            if (jsonReader.peek() != JsonToken.NAME) {
                jsonReader.skipValue();
            }
            switch (jsonReader.nextName()) {
                case "@context":
                    if (jsonReader.peek() != JsonToken.STRING) {
                        jsonReader.skipValue();
                        break;
                    }
                    if (!jsonReader.nextString().contains("schema.org")) {
                        stop = true;
                    }
                    break;
                case "@type":
                    if (jsonReader.peek() != JsonToken.STRING) {
                        jsonReader.skipValue();
                        break;
                    }
                    if (jsonReader.nextString().toLowerCase().equals("recipe")) {
                        recipeDTO = new RecipeDTO();
                    } else {
                        stop = true;
                    }
                    break;
                case "name":
                    if (jsonReader.peek() != JsonToken.STRING) {
                        jsonReader.skipValue();
                        break;
                    }
                    recipeDTO.setTitle(jsonReader.nextString());
                    break;
                case "image":
                    if (jsonReader.peek() != JsonToken.STRING) {
                        jsonReader.skipValue();
                        break;
                    }
                    recipeDTO.setImage(jsonReader.nextString());
                    break;
                case "description":
                    if (jsonReader.peek() != JsonToken.STRING) {
                        jsonReader.skipValue();
                        break;
                    }
                    recipeDTO.setDescription(jsonReader.nextString());
                    break;
                case "recipeInstructions":
                    if (jsonReader.peek() != JsonToken.BEGIN_ARRAY) {
                        jsonReader.skipValue();
                        break;
                    }
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        recipeDTO.addInstruction(jsonReader.nextString());
                    }
                    jsonReader.endArray();
                    break;
                case "recipeIngredient":
                    if (jsonReader.peek() != JsonToken.BEGIN_ARRAY) {
                        jsonReader.skipValue();
                        break;
                    }
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        recipeDTO.addIngredient(jsonReader.nextString());
                    }
                    jsonReader.endArray();
                    break;
                case "recipeCategory":
                    if (jsonReader.peek() != JsonToken.BEGIN_ARRAY) {
                        jsonReader.skipValue();
                        break;
                    }
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        recipeDTO.addCategory(jsonReader.nextString());
                    }
                    jsonReader.endArray();
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        if (!stop) {
            jsonReader.endObject();
        }

        return recipeDTO;
    }
}
