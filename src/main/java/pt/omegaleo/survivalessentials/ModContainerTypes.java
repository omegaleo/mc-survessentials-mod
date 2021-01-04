package pt.omegaleo.survivalessentials;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import pt.omegaleo.survivalessentials.client.gui.BackpackContainerScreen;
import pt.omegaleo.survivalessentials.client.gui.BookContainerScreen;
import pt.omegaleo.survivalessentials.inventory.BackpackContainer;
import pt.omegaleo.survivalessentials.containers.BookContainer;

/**
 * Mod {@link ContainerType}s and {@link net.minecraft.client.gui.screen.Screen} registration.
 * <p>
 * {@code ContainerTypes} are registered in {@link #registerContainerTypes(RegistryEvent.Register)}
 * <p>
 * {@code Screens} are registered in {@link #registerScreens(FMLClientSetupEvent)}.
 * <p>
 * {@code ContainerTypes} and {@link Container}s exist on both the client and server, and can be
 * used to send data between the two sides.
 * <p>
 * {@code Screens} exist only on the client, so make sure the server doesn't have any code that
 * references them. If needed, use the {@code @OnlyIn(Dist.CLIENT)} to remove code from the server.
 */
public final class ModContainerTypes {
    public static ContainerType<BackpackContainer> backpack;
    public static ContainerType<BookContainer> book;
    
    private ModContainerTypes() {}

    public static void registerContainerTypes(RegistryEvent.Register<ContainerType<?>> event) {
        backpack = register("backpack", new ContainerType<>(BackpackContainer::new));
        book = register("book", new ContainerType<>(BookContainer::new));
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerScreens(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(backpack, BackpackContainerScreen::new);
        ScreenManager.registerFactory(book, BookContainerScreen::new);
    }

    private static <T extends Container> ContainerType<T> register(String name, ContainerType<T> type) {
        ResourceLocation id = SurvivalEssentialsMod.getId(name);
        type.setRegistryName(id);
        ForgeRegistries.CONTAINERS.register(type);
        return type;
    }
}
