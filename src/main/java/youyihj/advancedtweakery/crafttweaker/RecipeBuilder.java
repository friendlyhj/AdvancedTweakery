package youyihj.advancedtweakery.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientStack;
import crafttweaker.api.item.WeightedItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.liquid.WeightedLiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.advancedtweakery.AdvancedTweakery;
import youyihj.advancedtweakery.actions.RecipeAdditionAction;
import zmaster587.libVulpes.recipe.RecipesMachine;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author youyihj
 */
@ZenRegister
@ZenClass("mods.advancedrocketry.RecipeBuilder")
public class RecipeBuilder {
    private final Class<?> clazz;
    private final List<List<ItemStack>> inputs = new ArrayList<>();
    private final List<FluidStack> fluidInputs = new ArrayList<>();
    private int itemInputIndex;
    private final List<RecipesMachine.ChanceItemStack> outputs = new ArrayList<>();
    private final List<RecipesMachine.ChanceFluidStack> fluidOutputs = new ArrayList<>();
    private final Map<Integer, String> oreDict = new HashMap<>();
    private int timeRequired;
    private int power;
    private int maxOutputSize = -1;

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
    public RecipeBuilder maxOutputSize(int maxOutputSize) {
        this.maxOutputSize = maxOutputSize;
        return this;
    }

    @ZenMethod
    public RecipeBuilder inputItem(IItemStack item) {
        inputs.add(Collections.singletonList(CraftTweakerMC.getItemStack(item)));
        itemInputIndex++;
        return this;
    }

    @ZenMethod
    public RecipeBuilder inputLiquid(ILiquidStack liquid) {
        fluidInputs.add(CraftTweakerMC.getLiquidStack(liquid));
        return this;
    }

    @ZenMethod
    public RecipeBuilder inputOre(IOreDictEntry oreDictEntry, @Optional(valueLong = 1L) int amount) {
        inputs.add(
                oreDictEntry.getItems().stream()
                        .map(it -> it.withAmount(amount))
                        .map(CraftTweakerMC::getItemStack)
                        .collect(Collectors.toList())
        );
        oreDict.put(itemInputIndex, oreDictEntry.getName());
        itemInputIndex++;
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
            return inputOre(((IOreDictEntry) ingredient), 1);
        }
        if (ingredient instanceof IngredientStack && ingredient.getInternal() instanceof IOreDictEntry) {
            return inputOre(((IOreDictEntry) ingredient.getInternal()), ingredient.getAmount());
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
    public RecipeBuilder outputItem(WeightedItemStack stack) {
        outputs.add(new RecipesMachine.ChanceItemStack(CraftTweakerMC.getItemStack(stack.getStack()), stack.getPercent()));
        return this;
    }

    @ZenMethod
    public RecipeBuilder outputLiquid(WeightedLiquidStack stack) {
        fluidOutputs.add(new RecipesMachine.ChanceFluidStack(CraftTweakerMC.getLiquidStack(stack.getStack()), stack.getPercent()));
        return this;
    }

    @ZenMethod
    public RecipeBuilder outputs(WeightedItemStack... items) {
        for (WeightedItemStack item : items) {
            outputItem(item);
        }
        return this;
    }

    @ZenMethod
    public RecipeBuilder outputs(WeightedLiquidStack... liquids) {
        for (WeightedLiquidStack liquid : liquids) {
            outputLiquid(liquid);
        }
        return this;
    }

    @ZenMethod
    public void build() {
        RecipesMachine.Recipe recipe = new RecipesMachine.Recipe(outputs, inputs, fluidOutputs, fluidInputs, timeRequired, power, oreDict);
        recipe.setMaxOutputSize(maxOutputSize);
        AdvancedTweakery.ACTIONS.add(new RecipeAdditionAction(clazz, recipe));
    }

    @ZenMethod
    public RecipeBuilder copy() {
        RecipeBuilder copy = new RecipeBuilder(clazz);
        copy.inputs.addAll(inputs);
        copy.fluidInputs.addAll(fluidInputs);
        copy.itemInputIndex = itemInputIndex;
        copy.outputs.addAll(outputs);
        copy.fluidOutputs.addAll(fluidOutputs);
        copy.oreDict.putAll(oreDict);
        copy.timeRequired = timeRequired;
        copy.power = power;
        copy.maxOutputSize = maxOutputSize;
        return copy;
    }
}
