package com.scrapper.scrapper.Recipe;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity(name = "recipe")
@AllArgsConstructor
@Getter
@Setter
public class Recipe {

    @Id
    @SequenceGenerator(
            name = "recipe_sequence",
            sequenceName = "recipe_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "recipe_sequence"
    )
    private Long id;
    private String name;
    private String kcal;
    private String protein;
    private String fat;
    private String carbohydrates;
    private String fiber;

    public Recipe(String name, String kcal, String protein, String fat, String carbohydrates, String fiber) {
        this.name = name;
        this.kcal = kcal;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.fiber = fiber;
    }

    public Recipe() {

    }
}
