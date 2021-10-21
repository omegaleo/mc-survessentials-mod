package pt.omegaleo.survivalessentials.world;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.ForgeRegistries;
import pt.omegaleo.survivalessentials.ModBlocks;
import pt.omegaleo.survivalessentials.util.enums.OreGenBlock;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountPlacement;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;

public class OreGeneration 
{
    static OreGenBlock[] OresToGenerateOverworld = new OreGenBlock[]
    {
        new OreGenBlock(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.CORUNDUM_ORE.get().getDefaultState(), 5, 15, 30, 10)
    };

    static OreGenBlock[] OresToGenerateNether = new OreGenBlock[]
    {
        new OreGenBlock(OreFeatureConfig.FillerBlockType.BASE_STONE_NETHER, ModBlocks.MYTHRIL_ORE.get().getDefaultState(), 5, 5, 20, 10)
    };
    
    public static void setupOreGeneration(final BiomeLoadingEvent event)
    {
        if(!event.getCategory().equals(Biome.Category.NETHER) && !event.getCategory().equals(Biome.Category.THEEND))
        {
            for(OreGenBlock ore : OresToGenerateOverworld)
            {
                event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, 
                    Feature.ORE.withConfiguration(new OreFeatureConfig(ore.fillerType, ore.state, ore.veinSize)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(ore.minHeight, 0, ore.maxHeight))).square().func_242731_b(ore.amount));
            }
        }
        else if(event.getCategory().equals(Biome.Category.NETHER))
        {
            for(OreGenBlock ore : OresToGenerateNether)
            {
                event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, 
                    Feature.ORE.withConfiguration(new OreFeatureConfig(ore.fillerType, ore.state, ore.veinSize)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(ore.minHeight, 0, ore.maxHeight))).square().func_242731_b(ore.amount));
            }
        }
    }
}
