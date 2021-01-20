package pt.omegaleo.survivalessentials;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import pt.omegaleo.survivalessentials.blocks.BlockEnchantmentExtractor;

public class ModBlocks {
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
                SurvivalEssentialsMod.MOD_ID);

        // Energy related blocks

        public static final RegistryObject<Block> enchantment_extractor = BLOCKS.register("enchantment_extractor_block",
                () -> new BlockEnchantmentExtractor());

}
