package youyihj.advancedtweakery.actions;

import crafttweaker.IAction;
import zmaster587.libVulpes.recipe.RecipesMachine;

/**
 * @author youyihj
 */
public class RecipeRemoveAllAction implements IAction {
    private final Class<?> clazz;

    public RecipeRemoveAllAction(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void apply() {
        RecipesMachine.getInstance().recipeList.get(clazz).clear();
    }

    @Override
    public String describe() {
        return "Removing all recipes of " + clazz.getName();
    }
}
