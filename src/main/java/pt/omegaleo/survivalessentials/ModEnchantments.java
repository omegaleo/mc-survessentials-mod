package pt.omegaleo.survivalessentials;

import java.util.Iterator;

import net.minecraft.client.gui.fonts.TexturedGlyph.Effect;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
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
        () -> new PotionEnchantment(EnchantmentType.ARMOR,  new EquipmentSlotType[] {EquipmentSlotType.CHEST}, 1));

    public static final RegistryObject<Enchantment> NIGHT_VISION = ENCHANTMENTS.register("night_vision",
        () -> new PotionEnchantment(EnchantmentType.ARMOR,  new EquipmentSlotType[] {EquipmentSlotType.HEAD}, 1));

    public static final RegistryObject<Enchantment> SWIFTNESS = ENCHANTMENTS.register("swiftness",
        () -> new PotionEnchantment(EnchantmentType.ARMOR,  new EquipmentSlotType[] {EquipmentSlotType.FEET}, 1));

    public static final RegistryObject<Enchantment> LEAPING = ENCHANTMENTS.register("leaping",
        () -> new PotionEnchantment(EnchantmentType.ARMOR,  new EquipmentSlotType[] {EquipmentSlotType.FEET}, 1));

    public static final RegistryObject<Enchantment> WATER_BREATHING = ENCHANTMENTS.register("water_breathing",
        () -> new PotionEnchantment(EnchantmentType.ARMOR,  new EquipmentSlotType[] {EquipmentSlotType.HEAD}, 1));

    public static final RegistryObject<Enchantment> HEALING = ENCHANTMENTS.register("healing",
        () -> new PotionEnchantment(EnchantmentType.ARMOR,  new EquipmentSlotType[] {EquipmentSlotType.CHEST}, 1));

    @SubscribeEvent
    public static void HeartsEnchant(LivingUpdateEvent event)
    {
        LivingEntity living = event.getEntityLiving();
        int level = 0;
        BlockPos pos = living.getPosition();
        World world = event.getEntity().world;
        if(living instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity)living;

            Iterable<ItemStack> armor = player.getArmorInventoryList();
            Iterator<ItemStack> iter = armor.iterator();

            while(iter.hasNext())
            {
                ItemStack currentArmorPiece = iter.next();
                level += EnchantmentHelper.getEnchantmentLevel(EXTRA_HEARTS.get(), currentArmorPiece);
            }

            AttributeModifier modifier = new AttributeModifier("MaxHealth", 1.75f * level, AttributeModifier.Operation.ADDITION);
            if(level > 0 && player.getAttribute(Attributes.MAX_HEALTH).getValue() < player.getAttribute(Attributes.MAX_HEALTH).getBaseValue() + (1.0f * level))
            {
                player.getAttribute(Attributes.MAX_HEALTH).applyPersistentModifier(modifier);
            }
            else if(level > 0 && player.getAttribute(Attributes.MAX_HEALTH).getValue() > player.getAttribute(Attributes.MAX_HEALTH).getBaseValue() + (1.0f * level))
            {
                player.getAttribute(Attributes.MAX_HEALTH).removeAllModifiers();
                player.getAttribute(Attributes.MAX_HEALTH).applyPersistentModifier(modifier);
            }
            else if(level == 0)
            {
                player.getAttribute(Attributes.MAX_HEALTH).removeAllModifiers();
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

        if(living instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity)living;

            Iterable<ItemStack> armor = player.getArmorInventoryList();
            Iterator<ItemStack> iter = armor.iterator();

            while(iter.hasNext())
            {
                ItemStack currentArmorPiece = iter.next();
                regenLevel += EnchantmentHelper.getEnchantmentLevel(REGEN.get(), currentArmorPiece);
                nightVisionLevel += EnchantmentHelper.getEnchantmentLevel(NIGHT_VISION.get(), currentArmorPiece);
                swiftnessLevel += EnchantmentHelper.getEnchantmentLevel(SWIFTNESS.get(), currentArmorPiece);
                leapingLevel += EnchantmentHelper.getEnchantmentLevel(LEAPING.get(), currentArmorPiece);
                waterLevel += EnchantmentHelper.getEnchantmentLevel(WATER_BREATHING.get(), currentArmorPiece);
                healingLevel += EnchantmentHelper.getEnchantmentLevel(HEALING.get(), currentArmorPiece);
            }

            EffectInstance regenEffect = new EffectInstance(Potions.REGENERATION.getEffects().get(0));
            EffectInstance nightVisionEffect = new EffectInstance(Potions.NIGHT_VISION.getEffects().get(0));
            EffectInstance swiftnessEffect = new EffectInstance(Potions.SWIFTNESS.getEffects().get(0));
            EffectInstance leapingEffect = new EffectInstance(Potions.LEAPING.getEffects().get(0));
            EffectInstance waterEffect = new EffectInstance(Potions.WATER_BREATHING.getEffects().get(0));
            EffectInstance healingEffect = new EffectInstance(Potions.HEALING.getEffects().get(0));

            if(regenLevel > 0)
            {
                player.addPotionEffect(regenEffect);
                
            }
            else if(regenLevel == 0)
            {
                player.removeActivePotionEffect(regenEffect.getPotion());
            }

            if(nightVisionLevel > 0)
            {
                player.addPotionEffect(nightVisionEffect);
                
            }
            else if(nightVisionLevel == 0)
            {
                player.removeActivePotionEffect(nightVisionEffect.getPotion());
            }

            if(swiftnessLevel > 0)
            {
                player.addPotionEffect(swiftnessEffect);
                
            }
            else if(nightVisionLevel == 0)
            {
                player.removeActivePotionEffect(swiftnessEffect.getPotion());
            }

            if(leapingLevel > 0)
            {
                player.addPotionEffect(leapingEffect);
                
            }
            else if(leapingLevel == 0)
            {
                player.removeActivePotionEffect(leapingEffect.getPotion());
            }

            if(waterLevel > 0)
            {
                player.addPotionEffect(waterEffect);
                
            }
            else if(waterLevel == 0)
            {
                player.removeActivePotionEffect(waterEffect.getPotion());
            }

            if(healingLevel > 0)
            {
                player.addPotionEffect(healingEffect);
                
            }
            else if(healingLevel == 0)
            {
                player.removeActivePotionEffect(healingEffect.getPotion());
            }
        }
    }
}
