package youyihj.advancedtweakery.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.advancedtweakery.AdvancedTweakery;
import youyihj.advancedtweakery.actions.RecipeRemovalAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author youyihj
 */
@ZenRegister
@ZenClass("mods.advancedrocketry.RecipeRemover")
public class RecipeRemover {
    private final Class<?> clazz;
    private final List<Object> outs = new ArrayList<>();

    public RecipeRemover(Class<?> clazz) {
        this.clazz = clazz;
    }

    @ZenMethod
    public RecipeRemover addOutputs(IItemStack... items) {
        outs.addAll(Arrays.asList(items));
        return this;
    }

    @ZenMethod
    public RecipeRemover addOutputs(ILiquidStack... liquids) {
        outs.addAll(Arrays.asList(liquids));
        return this;
    }

    @ZenMethod
    public void remove() {
        AdvancedTweakery.ACTIONS.add(new RecipeRemovalAction(clazz, outs.toArray()));
    }
}
