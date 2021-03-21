package pt.omegaleo.survivalessentials.util.tools;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;

public class ImprovedAxeItem extends ImprovedAxe 
{
    boolean isEnchanted = false;

    public ImprovedAxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, boolean isEnchanted) 
    {
        super(tier, attackDamageIn, attackSpeedIn, 10);

        this.isEnchanted = isEnchanted;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return isEnchanted;
    }
}
