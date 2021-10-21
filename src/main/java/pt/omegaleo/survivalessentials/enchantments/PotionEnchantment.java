package pt.omegaleo.survivalessentials.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class PotionEnchantment extends Enchantment {

    int _maxLevel = 0;

    public PotionEnchantment(EnchantmentType type, EquipmentSlotType[] slotTypes, int maxLevel) 
    {
        super(Rarity.VERY_RARE, type,  slotTypes);

        _maxLevel = maxLevel;
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
        return _maxLevel;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) 
    {
        return true;
    }
}
