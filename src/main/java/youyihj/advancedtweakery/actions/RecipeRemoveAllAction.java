package youyihj.advancedtweakery.actions;

import crafttweaker.IAction;
import youyihj.zenutils.api.reload.Reloadable;
import youyihj.zenutils.api.util.ReflectionInvoked;
import zmaster587.libVulpes.interfaces.IRecipe;
import zmaster587.libVulpes.recipe.RecipesMachine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author youyihj
 */
@Reloadable
public class RecipeRemoveAllAction implements IAction {
    private final Class<?> clazz;
    private final List<IRecipe> removedRecipes = new ArrayList<>();

    public RecipeRemoveAllAction(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void apply() {
        List<IRecipe> recipes = RecipesMachine.getInstance().recipeList.get(clazz);
        removedRecipes.addAll(recipes);
        recipes.clear();
    }

    @ReflectionInvoked
    public void undo() {
        RecipesMachine.getInstance().recipeList.get(clazz).addAll(removedRecipes);
    }

    @Override
    public String describe() {
        return "Removing all recipes of " + clazz.getName();
    }
}
