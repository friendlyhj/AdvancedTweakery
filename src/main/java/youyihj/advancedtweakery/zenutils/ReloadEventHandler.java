package youyihj.advancedtweakery.zenutils;

import crafttweaker.CraftTweakerAPI;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import youyihj.advancedtweakery.AdvancedTweakery;
import youyihj.zenutils.api.reload.ScriptReloadEvent;

/**
 * @author youyihj
 */
public class ReloadEventHandler {
    @SubscribeEvent
    public static void onReloadPre(ScriptReloadEvent.Pre event) {
        AdvancedTweakery.ACTIONS.clear();
    }

    @SubscribeEvent
    public static void onReloadPost(ScriptReloadEvent.Post event) {
        AdvancedTweakery.ACTIONS.forEach(CraftTweakerAPI::apply);
    }
}
