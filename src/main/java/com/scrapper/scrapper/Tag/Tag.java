package com.scrapper.scrapper.Tag;

import com.scrapper.scrapper.Recipe.Recipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "Tag")
@AllArgsConstructor
@Getter
@Setter
public class Tag {

    @Id
    @SequenceGenerator(
            name = "tagSequence",
            sequenceName = "tagSequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tagSequence"
    )
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Recipe> recipes = new HashSet<>();
    public Tag(String name) {
        this.name = name;
    }

    public Tag() {
    }
}
