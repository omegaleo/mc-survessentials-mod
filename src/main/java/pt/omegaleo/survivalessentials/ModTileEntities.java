package pt.omegaleo.survivalessentials;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import pt.omegaleo.survivalessentials.tileentity.TileEntityEnchantmentExtractor;

public class ModTileEntities 
{
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, SurvivalEssentialsMod.MOD_ID);
    
    public static final RegistryObject<TileEntityType<TileEntityEnchantmentExtractor>> enchantment_extractor = TILE_ENTITIES.register("enchantment_extractor", 
        () -> TileEntityType.Builder.create(TileEntityEnchantmentExtractor::new, ModBlocks.enchantment_extractor.get()).build(null));

}
