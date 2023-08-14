package com.scrapper.scrapper.Tag;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity(name = "Tag")
@AllArgsConstructor
@Getter
@Setter
public class Tag {

    @Id
    @SequenceGenerator(
            name = "tag_sequence",
            sequenceName = "tag_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tag_sequence"
    )
    private Long id;
    private String name;
    public Tag(String name) {
        this.name = name;
    }

    public Tag() {

    }
}
