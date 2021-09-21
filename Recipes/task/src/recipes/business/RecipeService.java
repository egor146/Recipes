package recipes.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.persistence.RecipeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe save(Recipe toSave) {
        return recipeRepository.save(toSave);
    }

    public Optional<Recipe> findRecipeById(Long id) {
        return recipeRepository.findRecipeById(id);
    }

    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

    public Optional<List<Recipe>> findByCategoryIgnoreCaseOrderByDateDesc(String category) {
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public Optional<List<Recipe>> findByNameContainsIgnoreCaseOrderByDateDesc(String name) {
        return recipeRepository.findByNameContainsIgnoreCaseOrderByDateDesc(name);
    }
}
