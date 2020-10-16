package pt.omegaleo.survivalessentials;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks 
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            SurvivalEssentialsMod.MOD_ID);

    //public static final RegistryObject<Item> RUBY = ITEMS.register("ruby",() -> new Item(new Item.Properties().group(SurvivalEssentialsMod.ITEMS_TAB)));

    //public static final RegistryObject<Block> IRON_SMOKER = BLOCKS.register("iron_smoker", () -> new IronSmoker());
}
