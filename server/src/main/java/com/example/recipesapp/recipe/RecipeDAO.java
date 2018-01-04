package com.example.recipesapp.recipe;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository("recipeDAO")
public class RecipeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public void getRecipeByIngredients(String ingredients) {
        @SuppressWarnings("unchecked")
        List<Long> result = entityManager
                .createQuery("SELECT DISTINCT id FROM recipes where fts(ingredients_tokens, '" + ingredients + "') = true")
                .getResultList();
        System.out.println(result);
    }
}
