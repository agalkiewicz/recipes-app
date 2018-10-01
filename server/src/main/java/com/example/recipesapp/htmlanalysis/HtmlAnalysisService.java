package com.example.recipesapp.htmlanalysis;

import com.example.recipesapp.exceptions.ScopeNotFoundException;
import com.example.recipesapp.recipe.Recipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HtmlAnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(HtmlAnalysisService.class);

    public Recipe analyse(String url) throws IOException, ScopeNotFoundException {

        if (!(url.contains("http://") || url.contains("https://"))) {
            url = "http://" + url;
        }

        Document document = Jsoup.connect(url).timeout(6000).get();

        Recipe recipe = null;

        long startTime = System.currentTimeMillis();
        try {
            recipe = analyseJson(document);
        } catch (Exception e) {
            logger.error(e.getMessage());
            recipe = null;
        }

        if (recipe == null) {
            recipe = analyseHtml(document);
        }
        long endTime = System.currentTimeMillis();

        logger.info("Execution time: {}", endTime - startTime);

        recipe.setUrl(url);

        return recipe;
    }

    private Recipe analyseJson(Document document) {
        Recipe recipe = null;
        Elements scriptElements = document.select("script[type=\"application/ld+json\"]");

        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Recipe.class, new RecipeAdapter());
        final Gson gson = gsonBuilder.create();

        for (Element scriptElement : scriptElements) {
            recipe = gson.fromJson(scriptElement.html(), Recipe.class);

            if (recipe != null) {
                break;
            }
        }

        return recipe;
    }

    private Recipe analyseHtml(Document document) throws ScopeNotFoundException {
        Recipe recipe = null;

        Elements elements = document.select("[itemtype*=schema.org/Recipe]");
        if (elements.size() != 0) {
            recipe = readStructuralData(elements, document, true);
        } else {
            elements = document.select("[typeOf*=Recipe]");
            if (elements.size() != 0) {
                recipe = readStructuralData(elements, document, false);
            } else {
                throw new ScopeNotFoundException();
            }
        }
        return recipe;
    }

    private Recipe readStructuralData(Elements elements, Document document, boolean isMicrodata) {
        Recipe recipe = new Recipe();
        StringBuilder stringBuilder = new StringBuilder();
        Element scope = elements.get(0);
        String property = isMicrodata ? "[itemprop=recipeIngredient]" : "[property=recipeIngredient]";
        elements = document.select(property);
        if (elements.size() == 0) {
            property = isMicrodata ? "[itemprop=ingredients]" : "[property=ingredients]";
            elements = document.select(property);
        }
        if (elements.size() != 0) {
            for (Element element : elements) {
                stringBuilder.append(element.text()).append("@");
            }
            recipe.setIngredients(stringBuilder.toString());
            stringBuilder.setLength(0);
        }

        property = isMicrodata ? "[itemprop=recipeInstructions]" : "[property=recipeInstructions]";
        elements = document.select(property);
        if (elements.size() != 0) {
            for (Element element : elements) {
                recipe.addStep(element.text());
            }
            stringBuilder.setLength(0);
        }

        property = isMicrodata ? "[itemprop=name]" : "[property=name]";
        System.out.println(scope);
        elements = scope.select(property);
        if (elements.size() != 0) {
            stringBuilder.append(elements.last().text());
            recipe.setTitle(stringBuilder.toString());
            stringBuilder.setLength(0);
        }

        property = isMicrodata ? "[itemprop=description]" : "[property=description]";
        elements = scope.select(property);
        if (elements.size() != 0) {
            stringBuilder.append(elements.first().text());
            recipe.setDescription(stringBuilder.toString());
            stringBuilder.setLength(0);
        }

        property = isMicrodata ? "[itemprop=image]" : "[property=image]";
        elements = scope.select(property);
        if (elements.size() != 0) {
            Element image = elements.first();
            elements = image.select("[src]");
            if (elements.size() == 0) {
                property = isMicrodata ? "[itemprop=url]" : "[property=url]";
                elements = scope.select(property);
            }
            if (elements.size() != 0) {
                String imageUrl = elements.last().absUrl("src");

                if (!(imageUrl.contains("http://") || imageUrl.contains("https://"))) {
                    imageUrl = "http://" + imageUrl;
                }
                recipe.setImage(imageUrl);
            }
        }

        return recipe;
    }
}
