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
        TypedQuery<Recipe> query = entityManager.createQuery("SELECT r FROM Recipe r WHERE ((r.user.id = '" + userId + "') AND (fts('" + ingredients + "') = true)) ORDER BY r.id DESC ", Recipe.class);
//        query.setParameter("userId", userId);
        List<Recipe> result = query.getResultList();

        return result;
    }
}
