package pt.omegaleo.survivalessentials;

import java.util.Iterator;
import java.util.Random;

import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import pt.omegaleo.survivalessentials.enchantments.ExtraHeartsEnchantment;
import pt.omegaleo.survivalessentials.enchantments.PotionEnchantment;

@Mod.EventBusSubscriber(modid = SurvivalEssentialsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister
            .create(ForgeRegistries.ENCHANTMENTS, SurvivalEssentialsMod.MOD_ID);

    public static final RegistryObject<Enchantment> EXTRA_HEARTS = ENCHANTMENTS.register("extrahearts",
        () -> new ExtraHeartsEnchantment());

    public static final RegistryObject<Enchantment> REGEN = ENCHANTMENTS.register("regen",
        () -> new PotionEnchantment(EnchantmentCategory.ARMOR,  new EquipmentSlot[] {EquipmentSlot.CHEST}, 1));

    public static final RegistryObject<Enchantment> NIGHT_VISION = ENCHANTMENTS.register("night_vision",
        () -> new PotionEnchantment(EnchantmentCategory.ARMOR,  new EquipmentSlot[] {EquipmentSlot.HEAD}, 1));

    public static final RegistryObject<Enchantment> SWIFTNESS = ENCHANTMENTS.register("swiftness",
        () -> new PotionEnchantment(EnchantmentCategory.ARMOR,  new EquipmentSlot[] {EquipmentSlot.FEET}, 1));

    public static final RegistryObject<Enchantment> LEAPING = ENCHANTMENTS.register("leaping",
        () -> new PotionEnchantment(EnchantmentCategory.ARMOR,  new EquipmentSlot[] {EquipmentSlot.FEET}, 1));

    public static final RegistryObject<Enchantment> WATER_BREATHING = ENCHANTMENTS.register("water_breathing",
        () -> new PotionEnchantment(EnchantmentCategory.ARMOR,  new EquipmentSlot[] {EquipmentSlot.HEAD}, 1));

    public static final RegistryObject<Enchantment> HEALING = ENCHANTMENTS.register("healing",
        () -> new PotionEnchantment(EnchantmentCategory.ARMOR,  new EquipmentSlot[] {EquipmentSlot.CHEST}, 1));

    @SubscribeEvent
    public static void HeartsEnchant(LivingUpdateEvent event)
    {
        LivingEntity living = event.getEntityLiving();
        int level = 0;
        var pos = living.getPosition(0f);
        Level world = event.getEntity().level;
        if(living instanceof Player)
        {
            Player player = (Player)living;

            level += EnchantmentHelper.getEnchantmentLevel(EXTRA_HEARTS.get(), living);

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
    public static void PotionEnchants(LivingUpdateEvent event)
    {
        LivingEntity living = event.getEntityLiving();
        int regenLevel = 0;
        int nightVisionLevel = 0;
        int swiftnessLevel = 0;
        int leapingLevel = 0;
        int waterLevel = 0;
        int healingLevel = 0;

        if(living instanceof Player)
        {
            Player player = (Player)living;

            regenLevel += EnchantmentHelper.getEnchantmentLevel(REGEN.get(), living);
            nightVisionLevel += EnchantmentHelper.getEnchantmentLevel(NIGHT_VISION.get(), living);
            swiftnessLevel += EnchantmentHelper.getEnchantmentLevel(SWIFTNESS.get(), living);
            leapingLevel += EnchantmentHelper.getEnchantmentLevel(LEAPING.get(), living);
            waterLevel += EnchantmentHelper.getEnchantmentLevel(WATER_BREATHING.get(), living);
            healingLevel += EnchantmentHelper.getEnchantmentLevel(HEALING.get(), living);

            MobEffectInstance regenEffect = new MobEffectInstance(Potions.REGENERATION.getEffects().get(0));
            MobEffectInstance nightVisionEffect = new MobEffectInstance(Potions.NIGHT_VISION.getEffects().get(0));
            MobEffectInstance swiftnessEffect = new MobEffectInstance(Potions.SWIFTNESS.getEffects().get(0));
            MobEffectInstance leapingEffect = new MobEffectInstance(Potions.LEAPING.getEffects().get(0));
            MobEffectInstance waterEffect = new MobEffectInstance(Potions.WATER_BREATHING.getEffects().get(0));
            MobEffectInstance healingEffect = new MobEffectInstance(Potions.HEALING.getEffects().get(0));

            if(regenLevel > 0)
            {
                player.addEffect(regenEffect);
                
            }
            else if(regenLevel == 0)
            {
                //player.removeEffect(regenEffect.getEffect());
            }

            if(nightVisionLevel > 0)
            {
                player.addEffect(nightVisionEffect);
                
            }
            else if(nightVisionLevel == 0)
            {
                //player.removeEffect(nightVisionEffect.getEffect());
            }

            if(swiftnessLevel > 0)
            {
                player.addEffect(swiftnessEffect);
                
            }
            else if(nightVisionLevel == 0)
            {
                //player.removeEffect(swiftnessEffect.getEffect());
            }

            if(leapingLevel > 0)
            {
                player.addEffect(leapingEffect);
                
            }
            else if(leapingLevel == 0)
            {
                //player.removeEffect(leapingEffect.getEffect());
            }

            if(waterLevel > 0)
            {
                player.addEffect(waterEffect);
                
            }
            else if(waterLevel == 0)
            {
                //player.removeEffect(waterEffect.getEffect());
            }

            if(healingLevel > 0)
            {
                player.addEffect(healingEffect);
                
            }
            else if(healingLevel == 0)
            {
                //player.removeEffect(healingEffect.getEffect());
            }
        }
    }

    @SubscribeEvent
    public static void LootModifications(LivingDeathEvent event)
    {
        Random r = new Random();

        int amount = r.nextInt(2);

        event.getEntity().spawnAtLocation(new ItemStack(ModItems.FOOD_LOOTBAG.get(), amount));

        if (event.getEntity() instanceof EnderMan)
        {
            double blockPlacerChance = r.nextDouble();

            if (blockPlacerChance <= 0.1)
            {
                event.getEntity().spawnAtLocation(new ItemStack(ModItems.BLOCK_PLACER.get(), 1));
            }
        }
    }
}
