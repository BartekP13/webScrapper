package com.scrapper.scrapper.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
    @Query("SELECT t FROM Tag t WHERE t.name = ?1")
    Tag findByName(String name);
}