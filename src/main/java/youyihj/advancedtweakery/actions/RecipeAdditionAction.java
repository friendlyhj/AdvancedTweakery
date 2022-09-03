package youyihj.advancedtweakery.actions;

import crafttweaker.IAction;
import zmaster587.libVulpes.recipe.RecipesMachine;

/**
 * @author youyihj
 */
public class RecipeAdditionAction implements IAction {
    private final Class<?> clazz;
    private final RecipesMachine.Recipe recipe;

    public RecipeAdditionAction(Class<?> clazz, RecipesMachine.Recipe recipe) {
        this.clazz = clazz;
        this.recipe = recipe;
    }

    @Override
    public void apply() {
        RecipesMachine.getInstance().getRecipes(clazz).add(recipe);
    }

    @Override
    public String describe() {
        return "Adding a recipe for " + clazz.getName();
    }
}
