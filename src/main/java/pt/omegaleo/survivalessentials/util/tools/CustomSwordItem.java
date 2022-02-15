package pt.omegaleo.survivalessentials.util.tools;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

import net.minecraft.world.item.Item.Properties;

public class CustomSwordItem extends SwordItem
{
    boolean isEnchanted = false;

    public CustomSwordItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder, boolean isEnchanted) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        
        this.isEnchanted = isEnchanted;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isEnchanted;
    }
}
