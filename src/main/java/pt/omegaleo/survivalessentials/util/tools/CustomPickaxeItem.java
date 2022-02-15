package pt.omegaleo.survivalessentials.util.tools;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;

import net.minecraft.world.item.Item.Properties;

public class CustomPickaxeItem extends PickaxeItem
{
    boolean isEnchanted = false;

    public CustomPickaxeItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder, boolean isEnchanted) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        
        this.isEnchanted = isEnchanted;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isEnchanted;
    }
}
