package pt.omegaleo.survivalessentials.util.tools;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class CustomSwordItem extends SwordItem
{
    boolean isEnchanted = false;

    public CustomSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder, boolean isEnchanted) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        
        this.isEnchanted = isEnchanted;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return isEnchanted;
    }
}
