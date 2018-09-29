package com.example.recipesapp.recipe;

import com.example.recipesapp.auth.User;
import com.example.recipesapp.auth.UserRepository;
import com.example.recipesapp.dto.*;
import com.example.recipesapp.exceptions.ScopeNotFoundException;
import com.example.recipesapp.htmlanalysis.HtmlAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "http://localhost:4200")
public class RecipeController {

    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);

    private final HtmlAnalysisService htmlAnalysisService;

    private final RecipeRepository recipeRepository;

    private final UserRepository userRepository;

    private final RecipeDAO recipeDAO;

    public RecipeController(HtmlAnalysisService htmlAnalysisService,
                            RecipeRepository recipeRepository,
                            UserRepository userRepository, RecipeDAO recipeDAO) {
        this.htmlAnalysisService = htmlAnalysisService;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.recipeDAO = recipeDAO;
    }

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> getAll() {
        try {
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
            List<Recipe> recipes = recipeRepository.findAllByIsDeletedFalseAndUserIdOrderByIdDesc(userId);

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
    public ResponseEntity add(@RequestBody RecipeUrlDTO recipeUrlDTO) {
        try {
            Recipe recipe = htmlAnalysisService.analyse(recipeUrlDTO.getUrl());
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = this.userRepository.findById(userId);
            recipe.setUser(user);
            recipeRepository.save(recipe);

            logger.info("Hardly created recipe: {}", recipe.toString());

            return new ResponseEntity<>(new RecipeDTO(recipe), HttpStatus.CREATED);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ScopeNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(new Error("Nie można dodać przepisu z tej strony. Strona nie implementuje znaczników Schema.org."), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (DataIntegrityViolationException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(new Error("Dodano już przepis z tej strony."), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> get(@PathVariable Long id) {
        try {
            Recipe foundRecipe = recipeRepository.findOne(id);
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!foundRecipe.getUser().getId().equals(userId)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            if (foundRecipe.isDeleted()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            RecipeDTO recipe = new RecipeDTO(foundRecipe);
            logger.info("Get a recipe to the client app: {}", recipe.toString());

            return new ResponseEntity<>(recipe, HttpStatus.OK);
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<RecipeDTO>> findByTerms(@RequestParam(value = "terms", required = true) List<String> terms) {
        try {
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
            String termsString = String.join(", ", terms);
            List<Recipe> recipes = recipeDAO.getRecipeByIngredients(termsString, userId);

            List<RecipeDTO> recipeDTOList = new ArrayList<>();
            if (!recipes.isEmpty()) {
                recipeDTOList = recipes.stream().map(recipe -> new RecipeDTO(recipe)).collect(Collectors.toList());
            }

            logger.info("Get recipes by terms: {}", recipeDTOList);

            return new ResponseEntity<>(recipeDTOList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id,
                                 @RequestBody HashMap<String, String> propertyToChange) {
        try {
            Recipe foundRecipe = recipeRepository.findOne(id);
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!foundRecipe.getUser().getId().equals(userId)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            if (propertyToChange.containsKey("isDeleted")) {
                foundRecipe.setDeleted(Boolean.valueOf(propertyToChange.get("isDeleted")));
            }
            recipeRepository.save(foundRecipe);

            logger.info("Mark recipe as deleted: {}", foundRecipe.toString());

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
