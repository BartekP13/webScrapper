package com.scrapper.scrapper.Ingredient;

import com.scrapper.scrapper.Recipe.Recipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "ingredient")
@AllArgsConstructor
@Getter
@Setter
public class Ingredient {

    @Id
    @SequenceGenerator(
            name = "ingredientSequence",
            sequenceName = "ingredientSequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ingredientSequence"
    )
    private Long id;
    private String name;
    private String quantity;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    public Ingredient(String name, String quantity, Recipe recipe) {
        this.name = name;
        this.quantity = quantity;
        this.recipe = recipe;
    }

    public Ingredient() {

    }
}
