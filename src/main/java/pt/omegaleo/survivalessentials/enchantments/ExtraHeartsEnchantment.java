package pt.omegaleo.survivalessentials.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class ExtraHeartsEnchantment extends Enchantment {

    public ExtraHeartsEnchantment() 
    {
        super(Rarity.VERY_RARE, EnchantmentType.ARMOR,  new EquipmentSlotType[] {EquipmentSlotType.CHEST, EquipmentSlotType.FEET, EquipmentSlotType.HEAD, EquipmentSlotType.LEGS});
    }
    
    @Override
    public int getMinEnchantability(int enchantmentLevel) 
    {
        return 20 * enchantmentLevel;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) 
    {
        return getMinEnchantability(enchantmentLevel) + 10;
    }

    @Override
    public int getMaxLevel() 
    {
        return 5;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) 
    {
        return true;
    }
}
