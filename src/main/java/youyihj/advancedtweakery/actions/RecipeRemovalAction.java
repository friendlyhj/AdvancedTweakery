package youyihj.advancedtweakery.actions;

import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.liquid.MCLiquidStack;
import net.minecraftforge.fluids.FluidStack;
import zmaster587.libVulpes.interfaces.IRecipe;
import zmaster587.libVulpes.recipe.RecipesMachine;

import java.util.List;

/**
 * @author youyihj
 */
public class RecipeRemovalAction implements IAction {
    private final Class<?> clazz;
    private final Object[] outs;

    public RecipeRemovalAction(Class<?> clazz, Object[] outs) {
        this.clazz = clazz;
        this.outs = outs;
    }

    @Override
    public void apply() {
        RecipesMachine.getInstance().recipeList.get(clazz).removeIf(this::matchesRecipe);
    }

    @Override
    public String describe() {
        return "Removing a recipe for " + clazz.getName();
    }

    private boolean matchesItem(List<RecipesMachine.ChanceItemStack> stacks, IItemStack crtStack) {
        return stacks.stream().anyMatch(it -> CraftTweakerMC.matches(crtStack, it.stack));
    }

    private boolean matchesFluid(List<FluidStack> stacks, ILiquidStack crtStack) {
        return stacks.stream().map(MCLiquidStack::new).anyMatch(crtStack::matches);
    }

    private boolean matchesRecipe(IRecipe rec) {
        if (rec instanceof RecipesMachine.Recipe) {
            RecipesMachine.Recipe recipe = (RecipesMachine.Recipe) rec;
            // quick checking
            if (outs.length != recipe.getChanceOutputs().size() + recipe.getFluidOutputs().size()) return false;
            for (Object out : outs) {
                if (out instanceof IItemStack) {
                    if (!matchesItem(recipe.getChanceOutputs(), ((IItemStack) out)))
                        return false;
                }
                if (out instanceof ILiquidStack) {
                    if (!matchesFluid(recipe.getFluidOutputs(), ((ILiquidStack) out)))
                        return false;
                }
            }
            return true;
        }
        return false;
    }
}
