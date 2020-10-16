package pt.omegaleo.survivalessentials;


import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("survivalessentials")
public class SurvivalEssentialsMod 
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "survivalessentials";

    public SurvivalEssentialsMod()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        //ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        //ModEntityType.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {}
    private void doClientStuff(final FMLClientSetupEvent event) { }

    // Custom ItemGroup TAB
    public static final ItemGroup ITEMS_TAB = new ItemGroup("survivalessentials_items") 
    {
        @Override
        public ItemStack createIcon() 
        {
            return new ItemStack(ModItems.RUBY.get());
        }
    };

    public static final ItemGroup ARMOR_TAB = new ItemGroup("survivalessentials_armor") 
    {
        @Override
        public ItemStack createIcon() 
        {
            return new ItemStack(ModItems.WOOD_CHESTPLATE.get());
        }
    };
}