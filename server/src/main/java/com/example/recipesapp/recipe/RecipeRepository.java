package com.example.recipesapp.recipe;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}