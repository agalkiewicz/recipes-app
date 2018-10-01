package com.example.recipesapp.recipe;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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

    public List<Recipe> getRecipeByIngredients(String ingredients, String userId) {
        String queryString = "SELECT r FROM Recipe r WHERE r.isDeleted = false AND r.user.id = '" + userId + "' AND text_search('" + ingredients + "') = true ORDER BY r.id DESC";
        TypedQuery<Recipe> query = entityManager.createQuery(queryString, Recipe.class);
        List<Recipe> result = query.getResultList();

        return result;
    }
}
