package com.scrapper.scrapper;

import com.scrapper.scrapper.Ingredient.Ingredient;
import com.scrapper.scrapper.Ingredient.IngredientRepository;
import com.scrapper.scrapper.Recipe.Recipe;
import com.scrapper.scrapper.Recipe.RecipeRepository;
import com.scrapper.scrapper.Tag.Tag;
import com.scrapper.scrapper.Tag.TagRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Service
public class ScrapperService {
    private static RecipeRepository recipeRepository;
    private static IngredientRepository ingredientRepository;
    private static TagRepository tagRepository ;

    @Autowired
    public ScrapperService(RecipeRepository recipeRepository,
                           IngredientRepository ingredientRepository,
                           TagRepository tagRepository) {
        ScrapperService.recipeRepository = recipeRepository;
        ScrapperService.ingredientRepository = ingredientRepository;
        ScrapperService.tagRepository = tagRepository;
    }

    public void Scrapper() {
        try {
            int page = 1;
            String urlFormat = "https://lionfitness.pl/przepisy/page/%d";
            while (page < 169) {
                String url = String.format(urlFormat, page);
                Document doc = Jsoup.connect(url).get();
                System.out.printf("\nWebsite Title: %s\n\n", doc.title());

                Elements repositories = doc.getElementsByClass("card-content");

                for (Element repository : repositories) {
                    Elements repositoryLink = repository.getElementsByClass("waves-effect waves-light btn cyan");
                    String link = repositoryLink.attr("href");
                    Document linkDoc = Jsoup.connect("https://lionfitness.pl/" + link).get();

                    Elements recipeCards = linkDoc.getElementsByClass("card-image waves-effect waves-block waves-light");
                    for (Element recipeCard : recipeCards) {
                        String title = recipeCard.getElementsByClass("card-title").text();

                        Elements cardContents = linkDoc.getElementsByClass("card-content");
                        for (Element cardContent : cardContents) {
                            Elements colElements = cardContent.select(".col.l4.s12");
                                String kcal = null, protein = null, fat = null, carbohydrates = null, fiber = null;
                                for (Element row : colElements) {
                                    Elements columns = row.select("td:nth-child(2)");
                                    for (int i = 0; i < columns.size(); i++) {
                                        String value = columns.get(i).text();
                                        try {
                                            switch (i) {
                                                case 0:
                                                    kcal = value;
                                                    break;
                                                case 1:
                                                    protein = value;
                                                    break;
                                                case 2:
                                                    fat = value;
                                                    break;
                                                case 3:
                                                    carbohydrates = value;
                                                    break;
                                                case 4:
                                                    fiber = value;
                                                    break;
                                                default:
                                                    break;
                                            }
                                        } catch (NumberFormatException e) {
                                            System.out.println("blad");
                                        }
                                    }
                                    Recipe recipe = new Recipe(title, kcal, protein, fat, carbohydrates, fiber);
                                    recipeRepository.save(recipe);

                                    Elements colElements1 = cardContent.select(".col.l8.s12");
                                    for (Element rows : colElements1) {
                                        Elements columns1 = rows.select("td:nth-child(1)");
                                        Elements columns2 = rows.select("td:nth-child(2)");

                                        for (int i = 0; i < columns1.size(); i++) {
                                            String value1 = columns1.get(i).text();
                                            String value2 = columns2.get(i).text();

                                            Ingredient ingredient = new Ingredient(value1, value2, recipe);
                                            ingredientRepository.save(ingredient);
                                        }
                                    }
                                    Elements tags = linkDoc.getElementsByClass("tags-list");
                                    for (Element tagList : tags) {
                                        Elements tagElements = tagList.select("li");

                                        for (Element tag : tagElements) {
                                            String tagText = tag.text();
                                            Tag existingTag = tagRepository.findByName(tagText);

                                            if (existingTag == null) {
                                                // Tworzenie nowego taga, jeśli nie istnieje
                                                Tag newTag = new Tag(tagText);
                                                tagRepository.save(newTag);

                                                // Dodanie taga do przepisu
                                                recipe.getTags().add(newTag);
                                            } else {
                                                // Dodanie istniejącego taga do przepisu
                                                recipe.getTags().add(existingTag);
                                            }
                                        }
                                    }
                                    recipeRepository.save(recipe);
                                }
                        }
                    }
                }
                page++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
