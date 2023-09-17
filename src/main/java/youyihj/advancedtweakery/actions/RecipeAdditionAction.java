package youyihj.advancedtweakery.actions;

import crafttweaker.IAction;
import youyihj.zenutils.api.reload.Reloadable;
import youyihj.zenutils.api.util.ReflectionInvoked;
import zmaster587.libVulpes.recipe.RecipesMachine;

/**
 * @author youyihj
 */
@Reloadable
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

    @ReflectionInvoked
    public void undo() {
        RecipesMachine.getInstance().getRecipes(clazz).remove(recipe);
    }

    @Override
    public String describe() {
        return "Adding a recipe for " + clazz.getName();
    }
}
