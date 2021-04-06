package pt.omegaleo.survivalessentials.util.tools;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;

public class CustomShovelItem extends ShovelItem
{
    boolean isEnchanted = false;

    public CustomShovelItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder, boolean isEnchanted) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        
        this.isEnchanted = isEnchanted;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return isEnchanted;
    }
}
