package pt.omegaleo.survivalessentials.util.tools;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;

public class CustomPickaxeItem extends PickaxeItem
{
    boolean isEnchanted = false;

    public CustomPickaxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder, boolean isEnchanted) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        
        this.isEnchanted = isEnchanted;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return isEnchanted;
    }
}
