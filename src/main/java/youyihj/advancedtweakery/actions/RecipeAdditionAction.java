package youyihj.advancedtweakery.actions;

import crafttweaker.IAction;
import zmaster587.libVulpes.recipe.RecipesMachine;

/**
 * @author youyihj
 */
public class RecipeAdditionAction implements IAction {
    private final Class<?> clazz;
    private final Object[] outs;
    private final int timeRequired;
    private final int power;
    private final Object[] inputs;

    public RecipeAdditionAction(Class<?> clazz, Object[] outs, int timeRequired, int power, Object[] inputs) {
        this.clazz = clazz;
        this.outs = outs;
        this.timeRequired = timeRequired;
        this.power = power;
        this.inputs = inputs;
    }

    @Override
    public void apply() {
        RecipesMachine.getInstance().addRecipe(clazz, outs, timeRequired, power, inputs);
    }

    @Override
    public String describe() {
        return "Adding a recipe for " + clazz.getName();
    }
}
