package pt.omegaleo.survivalessentials;

import java.util.Iterator;

import net.minecraft.client.gui.fonts.TexturedGlyph.Effect;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
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
import pt.omegaleo.survivalessentials.enchantments.RegenEnchantment;

@Mod.EventBusSubscriber(modid = SurvivalEssentialsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister
            .create(ForgeRegistries.ENCHANTMENTS, SurvivalEssentialsMod.MOD_ID);

    public static final RegistryObject<Enchantment> EXTRA_HEARTS = ENCHANTMENTS.register("extrahearts",
        () -> new ExtraHeartsEnchantment());

    public static final RegistryObject<Enchantment> REGEN = ENCHANTMENTS.register("regen",
        () -> new RegenEnchantment());


    @SubscribeEvent
    public static void ExtraHeartsEnchant(LivingUpdateEvent event)
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

            AttributeModifier modifier = new AttributeModifier("MaxHealth", 1.0f * level, AttributeModifier.Operation.ADDITION);
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
    public static void RegenEnchant(LivingUpdateEvent event)
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
                level += EnchantmentHelper.getEnchantmentLevel(REGEN.get(), currentArmorPiece);
            }

            EffectInstance regenEffect = Potions.REGENERATION.getEffects().get(0);

            if(level > 0)
            {
                player.addPotionEffect(regenEffect);
                
            }
            else if(level == 0)
            {
                player.removeActivePotionEffect(regenEffect.getPotion());
            }
        }
    }
}
