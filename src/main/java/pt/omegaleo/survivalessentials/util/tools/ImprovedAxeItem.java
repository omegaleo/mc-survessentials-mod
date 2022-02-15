package pt.omegaleo.survivalessentials.util.tools;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;

public class ImprovedAxeItem extends ImprovedAxe 
{
    boolean isEnchanted = false;

    public ImprovedAxeItem(Tier tier, int attackDamageIn, float attackSpeedIn, boolean isEnchanted) 
    {
        super(tier, attackDamageIn, attackSpeedIn, 10);

        this.isEnchanted = isEnchanted;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isEnchanted;
    }
}
