package com.scrapper.scrapper.Recipe;

import com.scrapper.scrapper.Ingredient.Ingredient;
import com.scrapper.scrapper.Tag.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "recipe")
@AllArgsConstructor
@Getter
@Setter
public class Recipe {

    @Id
    @SequenceGenerator(
            name = "recipeSequence",
            sequenceName = "recipeSequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "recipeSequence"
    )
    private Long id;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ingredient> ingredients = new HashSet<>();
    private String name;
    private String kcal;
    private String protein;
    private String fat;
    private String carbohydrates;
    private String fiber;

    @ManyToMany
    @JoinTable(
            name = "recipe_tag",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

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

    public Set<Tag> getTags() {
        return tags;
    }
}
