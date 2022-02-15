package pt.omegaleo.survivalessentials;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import pt.omegaleo.survivalessentials.blocks.CustomOreBlock;

public class ModBlocks {
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
                SurvivalEssentialsMod.MOD_ID);

        
        //Ores
        public static final RegistryObject<Block> CORUNDUM_ORE = BLOCKS.register("corundum_ore",
                () -> new CustomOreBlock());

        public static final RegistryObject<Block> MYTHRIL_ORE = BLOCKS.register("mythril_ore",
                () -> new CustomOreBlock());
}
