package com.example.recipesapp.htmlanalysis;

import com.example.recipesapp.recipe.Recipe;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.jsoup.Jsoup;

import java.io.IOException;

public class RecipeAdapter extends TypeAdapter {

    @Override
    public void write(JsonWriter jsonWriter, Object o) throws IOException {

    }

    @Override
    public Recipe read(final JsonReader jsonReader) throws IOException {
        Recipe recipe = null;
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
                        recipe = new Recipe();
                    } else {
                        stop = true;
                    }
                    break;
                case "name":
                    recipe.setTitle(readString(jsonReader));
                    break;
                case "image":
                    recipe.setImage(readString(jsonReader));
                    break;
                case "description":
                    recipe.setDescription(readString(jsonReader));
                    break;
                case "recipeInstructions":
                    recipe.setInstructions(readArray(jsonReader));
                    break;
                case "recipeIngredient":
                    recipe.setIngredients(readArray(jsonReader));
                    break;
                case "recipeCategory":
                    recipe.setCategories(readArray(jsonReader));
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        if (!stop) {
            jsonReader.endObject();
        }

        return recipe;
    }

    private String readArray(JsonReader jsonReader) throws IOException {
        String specialCharacters = "[\\n\\r\\t]";
        StringBuilder stringBuilder = new StringBuilder();
        String next = null;

        if (jsonReader.peek() != JsonToken.BEGIN_ARRAY && jsonReader.peek() != JsonToken.STRING) {
            jsonReader.skipValue();
            return null;
        }
        if (jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
            jsonReader.beginArray();
            if (jsonReader.peek() != JsonToken.STRING) {
                jsonReader.skipValue();
                return null;
            }
            while (jsonReader.hasNext()) {
                next = Jsoup.parse(jsonReader.nextString()).text();
                next = next.replaceAll(specialCharacters, "");
                stringBuilder.append(next).append("@");
            }
            jsonReader.endArray();
        } else if (jsonReader.peek() == JsonToken.STRING) {
            next = Jsoup.parse(jsonReader.nextString()).text();
            next = next.replaceAll(specialCharacters, "");
            stringBuilder.append(next);
        }

        return stringBuilder.toString();
    }

    private String readString(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() != JsonToken.STRING) {
            jsonReader.skipValue();
            return null;
        }
        return Jsoup.parse(jsonReader.nextString()).text();
    }
}
