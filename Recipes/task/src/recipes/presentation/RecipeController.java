package recipes.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.business.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class RecipeController {

    @Autowired
    RecipeService recipeService;
    @Autowired
    UserService userService;

    @PostMapping("/api/recipe/new")
    public Map<String, Long> add(@Valid @RequestBody Recipe recipe, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Optional<User> user = userService.findById(userDetails.getId());
        recipe.setUser(user.get());
        recipe.setDate(LocalDateTime.now());
        Recipe saved = recipeService.save(recipe);
        Long id = saved.getId();
        return Map.of("id", id);
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable long id) {
        Optional<Recipe> recipe = recipeService.findRecipeById(id);
        if (recipe.isPresent()) {
            return recipe.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/recipe/search")
    public List<Recipe> search(@RequestParam Map<String, String> params) {
        if (params.size() != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (params.containsKey("category")) {
            return recipeService.findByCategoryIgnoreCaseOrderByDateDesc(params.get("category")).
                    orElse(Collections.emptyList());
        }
        if (params.containsKey("name")) {
            return recipeService.findByNameContainsIgnoreCaseOrderByDateDesc(params.get("name")).
                    orElse(Collections.emptyList());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/api/recipe/{id}")
    public void update(@PathVariable long id, @Valid @RequestBody Recipe recipe, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Optional<Recipe> oldRecipe = recipeService.findRecipeById(id);
        if (oldRecipe.isPresent()) {
            if (oldRecipe.get().getUser().getId() == userDetails.getId()) {
                Recipe toUpdate = oldRecipe.get();
                toUpdate.setName(recipe.getName());
                toUpdate.setCategory(recipe.getCategory());
                toUpdate.setDate(LocalDateTime.now());
                toUpdate.setDescription(recipe.getDescription());
                toUpdate.setIngredients(recipe.getIngredients());
                toUpdate.setDirections(recipe.getDirections());
                recipeService.save(toUpdate);
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/recipe/{id}")
    public void delete(@PathVariable long id, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Optional<Recipe> recipe = recipeService.findRecipeById(id);
        if (recipe.isPresent()) {
            if (recipe.get().getUser().getId() == userDetails.getId()) {
                recipeService.deleteById(id);
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
