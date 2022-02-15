package pt.omegaleo.survivalessentials.enchantments;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.EquipmentSlot;

import net.minecraft.world.item.enchantment.Enchantment.Rarity;

public class ExtraHeartsEnchantment extends Enchantment {

    public ExtraHeartsEnchantment() 
    {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR,  new EquipmentSlot[] {EquipmentSlot.CHEST, EquipmentSlot.FEET, EquipmentSlot.HEAD, EquipmentSlot.LEGS});
    }

    @Override
    public int getMinCost(int enchantmentLevel)
    {
        return 20 * enchantmentLevel;
    }

    @Override
    public int getMaxCost(int enchantmentLevel)
    {
        return getMinCost(enchantmentLevel) + 10;
    }

    @Override
    public int getMaxLevel() 
    {
        return 5;
    }
}
