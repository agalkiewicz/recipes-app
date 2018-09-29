package com.example.recipesapp.htmlanalysis;

import com.example.recipesapp.recipe.Recipe;
import com.example.recipesapp.recipe.RecipeStep;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                    String image = "";
                    if (jsonReader.peek() == JsonToken.STRING) {
                        image = readString(jsonReader);
                    } else if (jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
                        jsonReader.beginArray();
                        if (jsonReader.peek() == JsonToken.STRING) {
                            image = readString(jsonReader);
                        } else if (jsonReader.peek() == JsonToken.BEGIN_OBJECT) {
                            image = readObject(jsonReader);
                        }
                        while (jsonReader.peek() != JsonToken.END_ARRAY) {
                            jsonReader.skipValue();
                        }
                        jsonReader.endArray();
                    }
                    recipe.setImage(image);
                    break;
                case "description":
                    recipe.setDescription(readString(jsonReader));
                    break;
                case "recipeInstructions":
                    if (jsonReader.peek() == JsonToken.STRING) {
                        recipe.addStep(readString(jsonReader));
                    } else if (jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
                        List<String> instructions = readArray(jsonReader);
                        for (String instruction : instructions) {
                            recipe.addStep(instruction);
                        }
                    }
                    break;
                case "recipeIngredient":
                    recipe.setIngredients(readStringOrArray(jsonReader));
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

    private String readArrayIntoString(JsonReader jsonReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String next = "";

        if (jsonReader.peek() != JsonToken.BEGIN_ARRAY) {
            jsonReader.skipValue();
            return null;
        }
        if (jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
            jsonReader.beginArray();
            if (jsonReader.peek() == JsonToken.STRING) {
                while (jsonReader.hasNext()) {
                    next = readString(jsonReader);
                    stringBuilder.append(next).append("@");
                }
            } else if (jsonReader.peek() == JsonToken.BEGIN_OBJECT) {
                while (jsonReader.hasNext()) {
                    next = readObject(jsonReader);
                    stringBuilder.append(next).append("@");
                }
            }
            jsonReader.endArray();
        }

        return stringBuilder.toString();
    }

    private List<String> readArray(JsonReader jsonReader) throws IOException {
        List<String> values = new ArrayList<>();

        if (jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
            jsonReader.beginArray();
            if (jsonReader.peek() == JsonToken.STRING) {
                while (jsonReader.hasNext()) {
                    values.add(readString(jsonReader));
                }
            } else if (jsonReader.peek() == JsonToken.BEGIN_OBJECT) {
                while (jsonReader.hasNext()) {
                    values.add(readObject(jsonReader));
                }
            }
            jsonReader.endArray();
        }
        return values;
    }

    private String readString(JsonReader jsonReader) throws IOException {
        String specialCharacters = "[\\n\\r\\t]";

        if (jsonReader.peek() != JsonToken.STRING) {
            jsonReader.skipValue();
            return null;
        }
        String word = jsonReader.nextString();
        return word.replaceAll(specialCharacters, "");
    }

    private String readObject(JsonReader jsonReader) throws IOException {
        String next = "";

        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            if (jsonReader.peek() != JsonToken.NAME) {
                jsonReader.skipValue();
            }
            switch (jsonReader.nextName()) {
                case "name":
                case "text":
                case "url":
                    next = readString(jsonReader);
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        return next;
    }

    private String readStringOrArray(JsonReader jsonReader) throws IOException {
        String word = "";
        if (jsonReader.peek() == JsonToken.STRING) {
            word = readString(jsonReader);
        } else if (jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
            word = readArrayIntoString(jsonReader);
        }
        return word;
    }
}
