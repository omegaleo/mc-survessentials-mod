package pt.omegaleo.survivalessentials;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import pt.omegaleo.survivalessentials.brewing.VinegarRecipe;
import pt.omegaleo.survivalessentials.client.ColorHandlers;
import pt.omegaleo.survivalessentials.world.OreGeneration;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("survivalessentials")
public class SurvivalEssentialsMod {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "survivalessentials";

    public SurvivalEssentialsMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ColorHandlers::registerItemColor);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(MenuType.class,
                ModContainerTypes::registerContainerTypes);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModContainerTypes::registerScreens);

        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModRecipes.RECIPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEnchantments.ENCHANTMENTS.register(FMLJavaModLoadingContext.get().getModEventBus());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        registerPotions();
        OreGeneration.registerOres();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }

    private void registerPotions() {
        BrewingRecipeRegistry.addRecipe(new VinegarRecipe());
    }

    // Custom ItemGroup TAB
    public static final CreativeModeTab ITEMS_TAB = new CreativeModeTab("survivalessentials_items") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.REDSTONE_INGOT.get());
        }
    };

    public static final CreativeModeTab ARMOR_TAB = new CreativeModeTab("survivalessentials_armor") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.WOOD_CHESTPLATE.get());
        }
    };

    public static final CreativeModeTab UPGRADES_TAB = new CreativeModeTab("survivalessentials_upgrades") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.TO_DIAMOND.get());
        }
    };

    public static final CreativeModeTab TOOLS_TAB = new CreativeModeTab("survivalessentials_tools") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.REDSTONE_PICKAXE.get());
        }
    };

    @Nonnull
    public static ResourceLocation getId(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
