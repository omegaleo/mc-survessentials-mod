package pt.omegaleo.survivalessentials.enchantments;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.EquipmentSlot;

import net.minecraft.world.item.enchantment.Enchantment.Rarity;

public class PotionEnchantment extends Enchantment {

    int _maxLevel = 0;

    public PotionEnchantment(EnchantmentCategory type, EquipmentSlot[] slotTypes, int maxLevel) 
    {
        super(Rarity.VERY_RARE, type,  slotTypes);

        _maxLevel = maxLevel;
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
        return _maxLevel;
    }
}
