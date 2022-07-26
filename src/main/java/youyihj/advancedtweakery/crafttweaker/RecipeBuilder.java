package youyihj.advancedtweakery.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.advancedtweakery.AdvancedTweakery;
import youyihj.advancedtweakery.actions.RecipeAdditionAction;
import zmaster587.libVulpes.recipe.NumberedOreDictStack;

import java.util.ArrayList;

/**
 * @author youyihj
 */
@ZenRegister
@ZenClass("mods.advancedrocketry.RecipeBuilder")
public class RecipeBuilder {
    private final Class<?> clazz;
    private final ArrayList<Object> inputs = new ArrayList<>();
    private final ArrayList<Object> outputs = new ArrayList<>();
    private int timeRequired;
    private int power;

    public RecipeBuilder(Class<?> clazz) {
        this.clazz = clazz;
    }

    @ZenMethod
    public RecipeBuilder timeRequired(int timeRequired) {
        this.timeRequired = timeRequired;
        return this;
    }

    @ZenMethod
    public RecipeBuilder power(int power) {
        this.power = power;
        return this;
    }

    @ZenMethod
    public RecipeBuilder inputItem(IItemStack item) {
        inputs.add(CraftTweakerMC.getItemStack(item));
        return this;
    }

    @ZenMethod
    public RecipeBuilder inputLiquid(ILiquidStack liquid) {
        inputs.add(CraftTweakerMC.getLiquidStack(liquid));
        return this;
    }

    @ZenMethod
    public RecipeBuilder inputOre(IOreDictEntry oreDictEntry) {
        inputs.add(new NumberedOreDictStack(oreDictEntry.getName(), oreDictEntry.getAmount()));
        return this;
    }

    @ZenMethod
    public RecipeBuilder input(IIngredient ingredient) {
        if (ingredient instanceof IItemStack) {
            return inputItem(((IItemStack) ingredient));
        }
        if (ingredient instanceof ILiquidStack) {
            return inputLiquid(((ILiquidStack) ingredient));
        }
        if (ingredient instanceof IOreDictEntry) {
            return inputOre(((IOreDictEntry) ingredient));
        }
        CraftTweakerAPI.logError("Unknown ingredient: " + ingredient + ". Ignore it...");
        return this;
    }

    @ZenMethod
    public RecipeBuilder inputs(IIngredient... ingredients) {
        for (IIngredient ingredient : ingredients) {
            input(ingredient);
        }
        return this;
    }

    @ZenMethod
    public RecipeBuilder outputItem(IItemStack stack) {
        outputs.add(CraftTweakerMC.getItemStack(stack));
        return this;
    }

    @ZenMethod
    public RecipeBuilder outputLiquid(ILiquidStack stack) {
        outputs.add(CraftTweakerMC.getLiquidStack(stack));
        return this;
    }

    @ZenMethod
    public RecipeBuilder outputs(IItemStack... items) {
        for (IItemStack item : items) {
            outputItem(item);
        }
        return this;
    }

    @ZenMethod
    public RecipeBuilder outputs(ILiquidStack... liquids) {
        for (ILiquidStack liquid : liquids) {
            outputLiquid(liquid);
        }
        return this;
    }

    @ZenMethod
    public void build() {
        AdvancedTweakery.ACTIONS.add(new RecipeAdditionAction(clazz, outputs.toArray(), timeRequired, power, inputs.toArray()));
    }
}
