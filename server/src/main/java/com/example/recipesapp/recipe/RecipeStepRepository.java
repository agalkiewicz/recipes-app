package com.example.recipesapp.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {
}

