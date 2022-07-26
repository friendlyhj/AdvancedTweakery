package youyihj.advancedtweakery.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.advancedtweakery.AdvancedTweakery;
import youyihj.advancedtweakery.actions.RecipeRemoveAllAction;
import zmaster587.advancedRocketry.block.BlockSmallPlatePress;

/**
 * @author youyihj
 */
@ZenRegister
@ZenClass("mods.advancedrocketry.RecipeTweaker")
public class RecipeTweaker {
    public static final String MACHINE_PACKAGE_ROOT = "zmaster587.advancedRocketry.tile.multiblock.machine.Tile";

    private final Class<?> clazz;

    public RecipeTweaker(Class<?> clazz) {
        this.clazz = clazz;
    }

    @ZenMethod
    public static RecipeTweaker forMachine(String name) {
        Class<?> machine;
        if (name.equals("SmallPlatePresser")) {
            machine = BlockSmallPlatePress.class;
        } else {
            try {
                machine = Class.forName(MACHINE_PACKAGE_ROOT + name);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("No such machine: " + name);
            }
        }
        return new RecipeTweaker(machine);
    }

    @ZenMethod
    public RecipeBuilder builder() {
        return new RecipeBuilder(clazz);
    }

    @ZenMethod
    public RecipeRemover remover() {
        return new RecipeRemover(clazz);
    }

    @ZenMethod
    public void removeAll() {
        AdvancedTweakery.ACTIONS.add(new RecipeRemoveAllAction(clazz));
    }
}
