package com.scrapper.scrapper.Ingredient;
import com.scrapper.scrapper.Ingredient.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
