package pt.omegaleo.survivalessentials.world;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import pt.omegaleo.survivalessentials.ModBlocks;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;

public class OreGeneration 
{
    public static final List<ConfiguredFeature<?, ?>> OVERWORLD_ORES = new ArrayList<>();
    public static final List<ConfiguredFeature<?, ?>> END_ORES = new ArrayList<>();
    public static final List<ConfiguredFeature<?, ?>> NETHER_ORES = new ArrayList<>();

    public static final RuleTest END_TEST = new BlockMatchTest(Blocks.END_STONE);

    public static void registerOres() {
        ConfiguredFeature<?, ?> corundumOre = Feature.ORE
                .configured(new OreConfiguration(List.of(
                        OreConfiguration.target(OreConfiguration.Predicates.STONE_ORE_REPLACEABLES,
                                ModBlocks.CORUNDUM_ORE.get().defaultBlockState())),
                        3))
                .rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(20)).squared().count(2);
        OVERWORLD_ORES.add(register(ModBlocks.CORUNDUM_ORE.get().getName().getString(), corundumOre));

        ConfiguredFeature<?, ?> deepSlateCorundumOre = Feature.ORE
                .configured(new OreConfiguration(List.of(
                        OreConfiguration.target(OreConfiguration.Predicates.DEEPSLATE_ORE_REPLACEABLES,
                                ModBlocks.CORUNDUM_ORE.get().defaultBlockState())),
                        3))
                .rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(20)).squared().count(2);
        OVERWORLD_ORES.add(register(ModBlocks.CORUNDUM_ORE.get().getName().getString(), deepSlateCorundumOre));

        ConfiguredFeature<?, ?> mythrilOre = Feature.ORE
                .configured(new OreConfiguration(List.of(OreConfiguration.target(
                        OreConfiguration.Predicates.NETHER_ORE_REPLACEABLES, ModBlocks.MYTHRIL_ORE.get().defaultBlockState())), 3))
                .rangeTriangle(VerticalAnchor.absolute(50), VerticalAnchor.absolute(120)).squared().count(2);
        NETHER_ORES.add(register(ModBlocks.MYTHRIL_ORE.get().getName().getString(), mythrilOre));

        ConfiguredFeature<?, ?> eggOre = Feature.ORE
                .configured(new OreConfiguration(
                        List.of(OreConfiguration.target(END_TEST, Blocks.GOLD_BLOCK.defaultBlockState())), 15))
                .rangeUniform(VerticalAnchor.absolute(20), VerticalAnchor.absolute(60)).squared().count(100);
        //END_ORES.add(register("egg", eggOre));
    }

    private static <Config extends FeatureConfiguration> ConfiguredFeature<Config, ?> register(String name,
                                                                                               ConfiguredFeature<Config, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(SurvivalEssentialsMod.MOD_ID, name),
                configuredFeature);
    }

    @Mod.EventBusSubscriber(modid = SurvivalEssentialsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeBusSubscriber {
        @SubscribeEvent
        public static void biomeLoading(BiomeLoadingEvent event) {
            List<Supplier<ConfiguredFeature<?, ?>>> features = event.getGeneration()
                    .getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);

            switch(event.getCategory()) {
                case NETHER -> OreGeneration.NETHER_ORES.forEach(ore -> features.add(() -> ore));
                case THEEND -> OreGeneration.END_ORES.forEach(ore -> features.add(() -> ore));
                default -> OreGeneration.OVERWORLD_ORES.forEach(ore -> features.add(() -> ore));
            }
        }
    }
}
