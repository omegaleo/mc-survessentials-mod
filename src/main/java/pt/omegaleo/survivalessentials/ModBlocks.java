package pt.omegaleo.survivalessentials;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import pt.omegaleo.survivalessentials.blocks.BlockEnchantmentExtractor;
import pt.omegaleo.survivalessentials.blocks.CustomOreBlock;

public class ModBlocks {
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
                SurvivalEssentialsMod.MOD_ID);


        public static final RegistryObject<Block> enchantment_extractor = BLOCKS.register("enchantment_extractor_block",
                () -> new BlockEnchantmentExtractor());

        
        //Ores
        public static final RegistryObject<Block> CORUNDUM_ORE = BLOCKS.register("corundum_ore",
                () -> new CustomOreBlock());
}
