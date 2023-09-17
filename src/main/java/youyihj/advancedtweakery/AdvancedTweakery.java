package youyihj.advancedtweakery;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import youyihj.advancedtweakery.zenutils.ReloadEventHandler;

import java.util.LinkedList;
import java.util.List;

@Mod(
        modid = AdvancedTweakery.MOD_ID,
        name = AdvancedTweakery.MOD_NAME,
        version = AdvancedTweakery.VERSION,
        dependencies = AdvancedTweakery.DEPENDENCIES
)
public class AdvancedTweakery {

    public static final String MOD_ID = "advancedtweakery";
    public static final String MOD_NAME = "AdvancedTweakery";
    public static final String VERSION = "1.1";
    public static final String DEPENDENCIES = "required-after:crafttweaker;required-after:advancedrocketry";

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static AdvancedTweakery INSTANCE;

    public static final List<IAction> ACTIONS = new LinkedList<>();

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        if (Loader.isModLoaded("zenutils")) {
            MinecraftForge.EVENT_BUS.register(ReloadEventHandler.class);
        }
    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        ACTIONS.forEach(CraftTweakerAPI::apply);
    }
}
