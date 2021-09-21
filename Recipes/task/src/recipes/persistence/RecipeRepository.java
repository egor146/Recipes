package recipes.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.business.Recipe;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Optional<Recipe> findRecipeById(Long id);
    Optional<List<Recipe>> findByCategoryIgnoreCaseOrderByDateDesc(String category);
    Optional<List<Recipe>> findByNameContainsIgnoreCaseOrderByDateDesc(String name);

}
