package pt.omegaleo.survivalessentials.registers;

import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;
import pt.omegaleo.survivalessentials.enchantments.ExtraHeartsEnchantment;
import pt.omegaleo.survivalessentials.enchantments.PotionEnchantment;

import java.util.Iterator;

public class EnchantmentsRegister
{
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister
            .create(Registries.ENCHANTMENT, SurvivalEssentialsMod.MODID);

    public static final DeferredHolder<Enchantment,Enchantment> EXTRA_HEARTS = ENCHANTMENTS.register("extrahearts",
            () -> new ExtraHeartsEnchantment(
                    EnchantmentCategory.ARMOR,
                    new EquipmentSlot[]
                    {
                            EquipmentSlot.CHEST,
                            EquipmentSlot.FEET,
                            EquipmentSlot.HEAD,
                            EquipmentSlot.LEGS
                    }));

    public static final DeferredHolder<Enchantment,Enchantment>  REGEN = ENCHANTMENTS.register("regen",
            () -> new PotionEnchantment(EnchantmentCategory.ARMOR,  new EquipmentSlot[] {EquipmentSlot.CHEST}));

    public static final DeferredHolder<Enchantment,Enchantment>  NIGHT_VISION = ENCHANTMENTS.register("night_vision",
            () -> new PotionEnchantment(EnchantmentCategory.ARMOR,  new EquipmentSlot[] {EquipmentSlot.HEAD}));

    public static final DeferredHolder<Enchantment,Enchantment>  SWIFTNESS = ENCHANTMENTS.register("swiftness",
            () -> new PotionEnchantment(EnchantmentCategory.ARMOR,  new EquipmentSlot[] {EquipmentSlot.FEET}));

    public static final DeferredHolder<Enchantment,Enchantment>  LEAPING = ENCHANTMENTS.register("leaping",
            () -> new PotionEnchantment(EnchantmentCategory.ARMOR,  new EquipmentSlot[] {EquipmentSlot.FEET}));

    public static final DeferredHolder<Enchantment,Enchantment>  WATER_BREATHING = ENCHANTMENTS.register("water_breathing",
            () -> new PotionEnchantment(EnchantmentCategory.ARMOR,  new EquipmentSlot[] {EquipmentSlot.HEAD}));

    public static final DeferredHolder<Enchantment,Enchantment>  HEALING = ENCHANTMENTS.register("healing",
            () -> new PotionEnchantment(EnchantmentCategory.ARMOR,  new EquipmentSlot[] {EquipmentSlot.CHEST}));
}
