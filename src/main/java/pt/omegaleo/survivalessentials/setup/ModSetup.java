package pt.omegaleo.survivalessentials.setup;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;
import pt.omegaleo.survivalessentials.world.OreGeneration;

@Mod.EventBusSubscriber(modid = SurvivalEssentialsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSetup {
    public static void setup() {
        IEventBus bus = MinecraftForge.EVENT_BUS;
        bus.addListener(OreGeneration::onBiomeLoadingEvent);
    }

    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            OreGeneration.registerOres();
        });
    }
}
