package pt.omegaleo.survivalessentials.registers;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredRegister;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;

public class CreativeTabRegister
{

    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SurvivalEssentialsMod.MODID);

}
