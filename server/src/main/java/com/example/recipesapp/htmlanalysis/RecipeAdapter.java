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
                    recipe.setInstructions(readStringOrArray(jsonReader));
                    break;
                case "recipeIngredient":
                    recipe.setIngredients(readStringOrArray(jsonReader));
                    break;
                case "recipeCategory":
                    recipe.setCategories(readStringOrArray(jsonReader));
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
        StringBuilder stringBuilder = new StringBuilder();
        String next = "";

        if (jsonReader.peek() != JsonToken.BEGIN_ARRAY && jsonReader.peek() != JsonToken.BEGIN_OBJECT) {
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

    private String readString(JsonReader jsonReader) throws IOException {
        String specialCharacters = "[\\n\\r\\t]";

        if (jsonReader.peek() != JsonToken.STRING) {
            jsonReader.skipValue();
            return null;
        }
        String word = Jsoup.parse(jsonReader.nextString()).text();
        return word.replaceAll(specialCharacters, "");
    }

    private String readObject(JsonReader jsonReader) throws IOException {
        String next = "";

        if (jsonReader.peek() != JsonToken.BEGIN_OBJECT) {
            jsonReader.skipValue();
            return null;
        }

        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            if (jsonReader.peek() != JsonToken.NAME) {
                jsonReader.skipValue();
            }
            switch (jsonReader.nextName()) {
                case "name":
                    if (jsonReader.peek() != JsonToken.STRING) {
                        jsonReader.skipValue();
                        break;
                    }
                    next = readString(jsonReader);
                    break;
                case "text":
                    if (jsonReader.peek() != JsonToken.STRING) {
                        jsonReader.skipValue();
                        break;
                    }
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
            word = readArray(jsonReader);
        }
        return word;
    }
}
