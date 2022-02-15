package pt.omegaleo.survivalessentials.world;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import pt.omegaleo.survivalessentials.ModBlocks;


public class OreGeneration 
{
    public static PlacedFeature OVERWORLD_ORES;
    public static PlacedFeature DEEPSLATE_ORES;
    public static PlacedFeature END_ORES;
    public static PlacedFeature NETHER_ORES;

    public static void registerOres()
    {
        OreConfiguration overworldConfig = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.CORUNDUM_ORE.get().defaultBlockState(), 3);
        OVERWORLD_ORES = registerPlacedFeature("corondum_ore", Feature.ORE.configured(overworldConfig),
                CountPlacement.of(3),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(20)));

        OreConfiguration deepSlateConfig = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.CORUNDUM_ORE.get().defaultBlockState(), 3);
        DEEPSLATE_ORES = registerPlacedFeature("corondum_ore_deepslate", Feature.ORE.configured(deepSlateConfig),
                CountPlacement.of(3),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(20)));

        OreConfiguration netherConfig = new OreConfiguration(OreFeatures.NETHER_ORE_REPLACEABLES, ModBlocks.MYTHRIL_ORE.get().defaultBlockState(), 3);
        NETHER_ORES = registerPlacedFeature("mythril_ore", Feature.ORE.configured(netherConfig),
                CountPlacement.of(3),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(50)));

        OreConfiguration endConfig = new OreConfiguration(OreFeatures.NETHER_ORE_REPLACEABLES, ModBlocks.MYTHRIL_ORE.get().defaultBlockState(), 3);
        END_ORES = registerPlacedFeature("mythril_ore_end", Feature.ORE.configured(endConfig),
                CountPlacement.of(3),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(50)));
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> PlacedFeature registerPlacedFeature(String registryName, ConfiguredFeature<C, F> feature, PlacementModifier... placementModifiers) {
        PlacedFeature placed = BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(registryName), feature).placed(placementModifiers);
        return PlacementUtils.register(registryName, placed);
    }

    public static void onBiomeLoadingEvent(BiomeLoadingEvent event)
    {
        if (event.getCategory() == Biome.BiomeCategory.NETHER)
        {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, NETHER_ORES);
        }
        else if (event.getCategory() == Biome.BiomeCategory.THEEND)
        {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, END_ORES);
        }
        else
        {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OVERWORLD_ORES);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, DEEPSLATE_ORES);
        }
    }

}
