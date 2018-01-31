package com.example.recipesapp.htmlanalysis;

import com.example.recipesapp.dto.RecipeDTO;
import com.example.recipesapp.exceptions.ScopeNotFoundException;
import com.example.recipesapp.recipe.Recipe;
import com.example.recipesapp.recipe.RecipeRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HtmlAnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(HtmlAnalysisService.class);

    private final RecipeRepository recipeRepository;

    public HtmlAnalysisService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }


    public RecipeDTO analyse(String url) throws IOException, ScopeNotFoundException {

        if (!(url.contains("http://") || url.contains("https://"))) {
            url = "http://" + url;
        }

        Document document = Jsoup.connect(url).get();
        Recipe recipe = new Recipe(url);
        RecipeDTO recipeDTO = null;

        try {
            recipeDTO = analyseJson(document);
        } catch (Exception e) {
            logger.error(e.getMessage());
            analyseHtml(document, recipe);
        }

        if (recipeDTO == null) {
            analyseHtml(document, recipe);
        } else {
            recipeDTO.setUrl(url);
            return recipeDTO;
        }

        return new RecipeDTO(recipe);
    }

    private RecipeDTO analyseJson(Document document) {
        Elements scriptElements = document.select("script[type=\"application/ld+json\"]");

        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(RecipeDTO.class, new RecipeDTOAdapter());
        final Gson gson = gsonBuilder.create();
        RecipeDTO recipe = null;

        for (Element scriptElement : scriptElements) {
            recipe = gson.fromJson(scriptElement.html(), RecipeDTO.class);

            if (recipe != null) {
                break;
            }
        }

        return recipe;
    }

    private void analyseHtml(Document document, Recipe recipe) throws ScopeNotFoundException {
        Elements elements = document.select("[itemtype*=schema.org/Recipe]");

        if (elements.size() != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            Element scope = elements.get(0);
            elements = scope.select("[itemprop=recipeIngredient]");
            if (elements.size() == 0) {
                elements = scope.select("[itemprop=ingredients]");
            }
            if (elements.size() != 0) {
                for (Element element : elements) {
                    logger.debug(element.text());
                    stringBuilder.append(element.text()).append("@");
                }
                recipe.setIngredients(stringBuilder.toString());
                stringBuilder.setLength(0);
            }

            elements = scope.select("[itemprop=recipeInstructions]");
            if (elements.size() != 0) {
                for (Element element : elements) {
                    logger.debug(element.text());
                    stringBuilder.append(element.text()).append("@");
                }
                recipe.setInstructions(stringBuilder.toString());
                stringBuilder.setLength(0);
            }

            elements = scope.select("[itemprop=name]");
            if (elements.size() != 0) {
                stringBuilder.append(elements.last().text());
                logger.debug(stringBuilder.toString());
                recipe.setTitle(stringBuilder.toString());
                stringBuilder.setLength(0);
            }

            elements = scope.select("[itemprop=description]");
            if (elements.size() != 0) {
                stringBuilder.append(elements.get(0).text());
                logger.debug(stringBuilder.toString());
                recipe.setDescription(stringBuilder.toString());
                stringBuilder.setLength(0);
            }

            elements = scope.select("[itemprop=image]");
            if (elements.size() != 0) {
                Element image = elements.get(0);
                String imageUrl = image.attr("src");

                if (!(imageUrl.contains("http://") || imageUrl.contains("https://"))) {
                    imageUrl = "http://" + imageUrl;
                }
                logger.debug(imageUrl);
                recipe.setImage(imageUrl);
            }

            elements = scope.select("[itemprop=recipeCategory]");
            if (elements.size() != 0) {
                for (Element element : elements) {
                    logger.debug(element.text());
                    stringBuilder.append(element.text()).append("@");
                }
                recipe.setCategories(stringBuilder.toString());
                stringBuilder.setLength(0);
            }

            recipeRepository.save(recipe);
        } else {
            throw new ScopeNotFoundException();
        }
    }
}
