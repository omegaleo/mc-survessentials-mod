package pt.omegaleo.survivalessentials.registers;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;

import java.util.ArrayList;
import java.util.List;

public class ItemRegister
{
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SurvivalEssentialsMod.MODID);
}
