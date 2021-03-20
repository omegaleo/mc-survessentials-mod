package pt.omegaleo.survivalessentials.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CustomItem extends Item
{
    boolean isEnchanted = false;
    public CustomItem(Properties properties, boolean isEnchanted) {
        super(properties);
        //TODO Auto-generated constructor stub

        this.isEnchanted = isEnchanted;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return isEnchanted;
    }
    
}
