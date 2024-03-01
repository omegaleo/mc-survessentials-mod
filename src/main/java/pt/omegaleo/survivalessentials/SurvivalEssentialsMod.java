package pt.omegaleo.survivalessentials;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;
import pt.omegaleo.survivalessentials.enchantments.ExtraHeartsEnchantment;
import pt.omegaleo.survivalessentials.registers.BlockRegister;
import pt.omegaleo.survivalessentials.registers.CreativeTabRegister;
import pt.omegaleo.survivalessentials.registers.EnchantmentsRegister;
import pt.omegaleo.survivalessentials.registers.ItemRegister;

@Mod(SurvivalEssentialsMod.MODID)
public class SurvivalEssentialsMod
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    static final ModConfigSpec SPEC = BUILDER.build();

    // Define mod id in a common place for everything to reference
    public static final String MODID = "survivalessentials";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    /*public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.examplemod")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());*/

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public SurvivalEssentialsMod(IEventBus modEventBus)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BlockRegister.BLOCKS.register(modEventBus);

        // Register the Deferred Register to the mod event bus so items get registered
        ItemRegister.ITEMS.register(modEventBus);

        // Register the Deferred Register to the mod event bus so tabs get registered
        CreativeTabRegister.CREATIVE_MODE_TABS.register(modEventBus);

        // Register enchantments
        EnchantmentsRegister.ENCHANTMENTS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        /*if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(EXAMPLE_BLOCK_ITEM);*/
    }

    @SubscribeEvent
    private void HeartsEnchant(LivingEquipmentChangeEvent event)
    {
        var living = event.getEntity();
        int level = 0;
        var pos = living.getPosition(1);
        var world = event.getEntity().getCommandSenderWorld();
        if(living instanceof Player)
        {
            var player = (Player)living;

            level = EnchantmentHelper.getEnchantmentLevel(EnchantmentsRegister.EXTRA_HEARTS.get(), player);

            AttributeModifier modifier = new AttributeModifier("MaxHealth", 1.75f * level, AttributeModifier.Operation.ADDITION);
            if(level > 0 && player.getAttribute(Attributes.MAX_HEALTH).getValue() < player.getAttribute(Attributes.MAX_HEALTH).getBaseValue() + (1.0f * level))
            {
                player.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(modifier);
            }
            else if(level > 0 && player.getAttribute(Attributes.MAX_HEALTH).getValue() > player.getAttribute(Attributes.MAX_HEALTH).getBaseValue() + (1.0f * level))
            {
                player.getAttribute(Attributes.MAX_HEALTH).removeModifiers();
                player.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(modifier);
            }
            else if(level == 0)
            {
                player.getAttribute(Attributes.MAX_HEALTH).removeModifiers();
            }
        }
    }


    @SubscribeEvent
    private void PotionEnchants(LivingEvent.LivingTickEvent event)
    {
        var living = event.getEntity();
        int regenLevel = 0;
        int nightVisionLevel = 0;
        int swiftnessLevel = 0;
        int leapingLevel = 0;
        int waterLevel = 0;
        int healingLevel = 0;

        if(living instanceof Player)
        {
            var player = (Player)living;

            regenLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentsRegister.REGEN.get(), player);
            nightVisionLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentsRegister.NIGHT_VISION.get(), player);
            swiftnessLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentsRegister.SWIFTNESS.get(), player);
            leapingLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentsRegister.LEAPING.get(), player);
            waterLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentsRegister.WATER_BREATHING.get(), player);
            healingLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentsRegister.HEALING.get(), player);

            var regenEffect = new MobEffectInstance(Potions.REGENERATION.getEffects().get(0));
            var nightVisionEffect = new MobEffectInstance(Potions.NIGHT_VISION.getEffects().get(0));
            var swiftnessEffect = new MobEffectInstance(Potions.SWIFTNESS.getEffects().get(0));
            var leapingEffect = new MobEffectInstance(Potions.LEAPING.getEffects().get(0));
            var waterEffect = new MobEffectInstance(Potions.WATER_BREATHING.getEffects().get(0));
            var healingEffect = new MobEffectInstance(Potions.HEALING.getEffects().get(0));

            if(regenLevel > 0)
            {
                player.addEffect(regenEffect);

            }
            else if(regenLevel == 0)
            {
                player.removeEffect(regenEffect.getEffect());
            }

            if(nightVisionLevel > 0)
            {
                player.addEffect(nightVisionEffect);

            }
            else if(nightVisionLevel == 0)
            {
                player.removeEffect(nightVisionEffect.getEffect());
            }

            if(swiftnessLevel > 0)
            {
                player.addEffect(swiftnessEffect);

            }
            else if(nightVisionLevel == 0)
            {
                player.removeEffect(swiftnessEffect.getEffect());
            }

            if(leapingLevel > 0)
            {
                player.addEffect(leapingEffect);

            }
            else if(leapingLevel == 0)
            {
                player.removeEffect(leapingEffect.getEffect());
            }

            if(waterLevel > 0)
            {
                player.addEffect(waterEffect);

            }
            else if(waterLevel == 0)
            {
                player.removeEffect(waterEffect.getEffect());
            }

            if(healingLevel > 0)
            {
                player.addEffect(healingEffect);

            }
            else if(healingLevel == 0)
            {
                player.removeEffect(healingEffect.getEffect());
            }
        }
    }

}
