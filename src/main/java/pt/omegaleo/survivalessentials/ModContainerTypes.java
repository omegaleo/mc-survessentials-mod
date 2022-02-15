package pt.omegaleo.survivalessentials;

import com.mojang.blaze3d.platform.ScreenManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.registries.ForgeRegistries;
import pt.omegaleo.survivalessentials.client.gui.BackpackContainerScreen;
import pt.omegaleo.survivalessentials.client.gui.BlockPlacerContainerScreen;
import pt.omegaleo.survivalessentials.client.gui.BookContainerScreen;
import pt.omegaleo.survivalessentials.client.gui.DrillContainerScreen;
import pt.omegaleo.survivalessentials.client.gui.ItemFilterContainerScreen;
import pt.omegaleo.survivalessentials.inventory.BackpackContainer;
import pt.omegaleo.survivalessentials.inventory.BlockPlacerContainer;
import pt.omegaleo.survivalessentials.inventory.ItemFilterContainer;
import pt.omegaleo.survivalessentials.containers.BookContainer;
import pt.omegaleo.survivalessentials.containers.DrillContainer;

public final class ModContainerTypes {
    public static MenuType<BackpackContainer> backpack;
    public static MenuType<BookContainer> book;
    public static MenuType<DrillContainer> drill;
    public static MenuType<ItemFilterContainer> itemFilter;
    public static MenuType<BlockPlacerContainer> blockPlacerContainer;

    private ModContainerTypes() {}

    public static void registerContainerTypes(RegistryEvent.Register<MenuType<?>> event) {
        backpack = register("backpack", new MenuType<>(BackpackContainer::new));
        book = register("book", new MenuType<>(BookContainer::new));
        drill = register("drill", new MenuType<>(DrillContainer::new));
        itemFilter = register("itemfilter", new MenuType<>(ItemFilterContainer::new));
        blockPlacerContainer = register("blockplacercontainer", new MenuType<>(BlockPlacerContainer::new));
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerScreens(FMLClientSetupEvent event) 
    {
        MenuScreens.register(backpack, BackpackContainerScreen::new);
        MenuScreens.register(book, BookContainerScreen::new);
        MenuScreens.register(drill, DrillContainerScreen::new);
        MenuScreens.register(itemFilter, ItemFilterContainerScreen::new);
        MenuScreens.register(blockPlacerContainer, BlockPlacerContainerScreen::new);
    }

    private static <T extends AbstractContainerMenu> MenuType<T> register(String name, MenuType<T> type) {
        ResourceLocation id = SurvivalEssentialsMod.getId(name);
        type.setRegistryName(id);
        ForgeRegistries.CONTAINERS.register(type);
        return type;
    }
}
