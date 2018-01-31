package com.example.recipesapp.recipe;

import com.example.recipesapp.dto.RecipeDTO;
import com.example.recipesapp.dto.RecipeUrlDTO;
import com.example.recipesapp.exceptions.ScopeNotFoundException;
import com.example.recipesapp.htmlanalysis.HtmlAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/recipes")
@CrossOrigin(origins = "http://localhost:4200")
public class RecipeController {

    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);

    private final HtmlAnalysisService htmlAnalysisService;

    private final RecipeRepository recipeRepository;

    public RecipeController(HtmlAnalysisService htmlAnalysisService,
                            RecipeRepository recipeRepository) {
        this.htmlAnalysisService = htmlAnalysisService;
        this.recipeRepository = recipeRepository;
    }

    @GetMapping()
    ResponseEntity<List<RecipeDTO>> getAll() {
        try {
            List<Recipe> recipes = recipeRepository.findAll();

            List<RecipeDTO> recipeDTOList = new ArrayList<>();
            for (Recipe recipe : recipes) {
                recipeDTOList.add(new RecipeDTO(recipe));
            }
            logger.info("Returned list of recipes: {}", recipeDTOList);

            return new ResponseEntity<>(recipeDTOList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<RecipeDTO> add(@RequestBody RecipeUrlDTO recipeUrlDTO) {
        try {
            RecipeDTO recipe = htmlAnalysisService.analyse(recipeUrlDTO.getUrl());
            logger.info("Hardly created recipe: {}", recipe.toString());

            return new ResponseEntity<>(recipe, HttpStatus.CREATED);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ScopeNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
