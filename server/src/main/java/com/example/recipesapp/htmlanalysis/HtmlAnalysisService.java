package com.example.recipesapp.htmlanalysis;

import com.example.recipesapp.dto.RecipeDTO;
import com.example.recipesapp.recipe.Recipe;
import com.example.recipesapp.recipe.RecipeRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HtmlAnalysisService {

    private final RecipeRepository recipeRepository;

    public HtmlAnalysisService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public RecipeDTO analyse(String url) throws IOException {
        Recipe recipe = new Recipe();

        if (!(url.contains("http://") || url.contains("https://"))) {
            String protocol = "http://";
            url = protocol + url;
        }

        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select("[itemprop=recipeIngredient]");

        StringBuilder ingredients = new StringBuilder();
        for (Element element : elements) {
            System.out.println(element.text());
            ingredients.append(element.text() + "@");
        }
        recipe.setIngredients(ingredients.toString());
        System.out.println(recipe);

        recipeRepository.save(recipe);

        return new RecipeDTO(recipe);
    }
}
