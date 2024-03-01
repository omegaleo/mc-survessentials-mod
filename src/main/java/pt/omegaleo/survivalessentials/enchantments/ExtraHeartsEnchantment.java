package pt.omegaleo.survivalessentials.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ExtraHeartsEnchantment extends Enchantment
{
    public ExtraHeartsEnchantment(EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots)
    {
        super(Rarity.VERY_RARE, pCategory, pApplicableSlots);
    }

    @Override
    public int getMaxLevel()
    {
        return 5;
    }
}
